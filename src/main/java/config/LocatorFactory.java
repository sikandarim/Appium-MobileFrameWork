package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

import java.io.File;
import java.util.Map;

public class LocatorFactory {
    private static Map<String, Locator> locatorMap;

    static {
        try {
            String jsonPath = System.getProperty("user.dir") + "/src/test/resources/deviceRunner.json";
            String platform = PropertyReader.getPlatformName(jsonPath);
            ObjectMapper mapper = new ObjectMapper();
            if (platform.equals("Android")) {
                locatorMap = mapper.readValue(
                        new File( System.getProperty("user.dir") + "/src/test/resources/locators/Android.json"),
                        new TypeReference<>() {}
                );
            } else if (platform.equals("iOS")) {
                locatorMap = mapper.readValue(
                        new File( System.getProperty("user.dir") + "/src/test/resources/locators/iOS.json"),
                        new TypeReference<>() {}
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static By get(String key) {
        Locator loc = locatorMap.get(key);
        if (loc == null) {
            throw new IllegalArgumentException("Locator not found: " + key);
        }

        return switch (loc.type.toLowerCase()) {
            case "id" -> By.id(loc.value);
            case "xpath" -> By.xpath(loc.value);
            case "accessibilityid" -> MobileBy.AccessibilityId(loc.value);
            case "androiduiautomator" -> MobileBy.AndroidUIAutomator(loc.value);
            case "classname" -> By.className(loc.value);
            case "iosclasschain" -> MobileBy.iOSClassChain(loc.value);
            case "iosnspredicate" -> MobileBy.iOSNsPredicateString(loc.value);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + loc.type);
        };
    }
}
