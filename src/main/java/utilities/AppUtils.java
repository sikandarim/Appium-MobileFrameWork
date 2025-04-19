package utilities;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    /**
     * 📸 Allure-compatible screenshot attachment
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshot(AppiumDriver driver) {
        try {
            if (driver == null) return new byte[0];
            return driver.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return new byte[0];
        }
    }

    /**
     * 💾 Save screenshot to disk (optional: call manually for debug use)
     */
    public static void saveScreenshotToFile(AppiumDriver driver, String scenarioName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = "target/screenshots/" + scenarioName + "_" + getCurrentTimeStamp() + ".png";
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            System.out.println("Failed to save screenshot to file: " + e.getMessage());
        }
    }

    /**
     * 🕒 Timestamp for filename usage
     */
    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    /**
     * ⏳ Sleep utility
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
