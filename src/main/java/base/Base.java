package base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hooks.Hooks;
import io.appium.java_client.AppiumDriver;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Base {

     protected final AppiumDriver driver;

    public Base () {
        this.driver = Hooks.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException(" Appium driver was not initialized. Check Hooks class.");
        }
    }
    public Map<String, String> getLoginCredentials(String userKey) {
        Map<String, String> credentials = new HashMap<>();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("dataList.json");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(input);
            JsonNode loginNode = root.path("login").path(userKey);

            if (!loginNode.isMissingNode()) {
                credentials.put("username", loginNode.path("username").asText());
                credentials.put("password", loginNode.path("password").asText());
            } else {
                System.out.println("No credentials found for userKey: " + userKey);
            }
        } catch (Exception e) {
            System.out.println("Failed to load login credentials: " + e.getMessage());
        }

        return credentials;
    }


}
