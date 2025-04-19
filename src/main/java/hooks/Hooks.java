package hooks;

import config.DeviceConfigLoader;
import config.PropertyReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.remote.DesiredCapabilities;
import utilities.AppUtils;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static utilities.AppUtils.attachScreenshot;

public class Hooks {

    private static AppiumDriver driver;

    @Before
    public void setUp(Scenario scenario) throws Exception {
        System.out.println("🚀 Starting scenario: " + scenario.getName());

        String jsonPath = System.getProperty("user.dir") + "/src/test/resources/deviceRunner.json";
        String platform = PropertyReader.getPlatformName(jsonPath);

        if (platform == null || platform.isEmpty()) {
            throw new IllegalArgumentException("❌ Platform name is null or empty — check deviceRunner.json.");
        }

        DesiredCapabilities caps = DeviceConfigLoader.loadCapabilities(platform);
        URL url = new URL("http://0.0.0.0:4723");

        switch (platform.toLowerCase()) {
            case "android" -> driver = new AndroidDriver(url, caps);
            case "ios" -> driver = new IOSDriver(url, caps);
            default -> throw new IllegalArgumentException("❌ Unsupported platform: " + platform);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println("✅ Driver launched successfully on: " + platform);
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        AppUtils.attachScreenshot(Hooks.getDriver()); // or just `driver` if available
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("🔚 Finished scenario: " + scenario.getName());

        if (scenario.isFailed() && driver != null) {
            try {
                AppUtils.attachScreenshot(Hooks.getDriver()); // One final screenshot on failure
            } catch (Exception e) {
                System.out.println("Screenshot capture failed: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println(" Driver session closed.");
        }
    }

    public static AppiumDriver getDriver() {
        return driver;
    }
}
