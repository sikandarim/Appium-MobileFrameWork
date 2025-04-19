package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.InputStream;
import java.util.Map;

public class DeviceConfigLoader {

    public static DesiredCapabilities loadCapabilities(String platform) throws Exception {
        ObjectMapper mapper = new ObjectMapper();


        InputStream input = DeviceConfigLoader.class.getClassLoader()
                                                    .getResourceAsStream("deviceList.json");

        if (input == null) {
            throw new IllegalArgumentException("Could not find deviceList.json in resources folder.");
        }

        Map<String, Map<String, Object>> fullConfig = mapper.readValue(input, new TypeReference<>() {});
        Map<String, Object> selectedConfig = fullConfig.get(platform.toLowerCase());

        if (selectedConfig == null) {
            throw new IllegalArgumentException("Platform not found in deviceList.json: " + platform);
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        selectedConfig.forEach(caps::setCapability);

        return caps;
    }
}
