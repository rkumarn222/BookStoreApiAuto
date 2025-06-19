package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class configController {
    private static final Properties properties = new Properties();
    private static boolean initialized = false;

    public static synchronized void setUp() {
        if (initialized) {
            return;
        }
        String env = System.getProperty("env", "UATB"); // Default to UATB if not specified
        String configFileName = "application-" + env + ".properties";
        String configFilePath = "src/test/resources/" + configFileName;

        try (InputStream input = configController.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new RuntimeException("Properties file not found: " + configFilePath);
            }
            properties.load(input);
            initialized = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + configFilePath, e);
        }
    }

    public static String get(String key) {
        if (!initialized) {
            setUp();
        }
        return properties.getProperty(key);
    }
}


