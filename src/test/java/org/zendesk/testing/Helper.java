package org.zendesk.testing;

import com.google.common.io.Resources;
import io.restassured.path.json.JsonPath;
import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 * Utility class
 */
public class Helper {
    private static Properties config = null;
    private static String configFile;

    public static String readFile(String path) {
        URL url = Resources.getResource(path);
        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file:" + path);
        }
    }

    public static InputStream getResourceAsStream(String path) {
        return Helper.class.getResourceAsStream(path);
    }


    public static Properties readConfigFile(String path) {
        if (!path.isEmpty()) {
            config = new Properties();
            InputStream is = Helper.getResourceAsStream("/" + path);
            try {
                config.load(is);
            } catch (IOException e) {
                throw new RuntimeException("Can't load user settings file" + e);
            }
        }
        else {
            throw new RuntimeException("Please provide user configuration");
        }
        return config;
    }

    public static Properties getConfig() {
        if (config == null) {
            config = readConfigFile(configFile);
        }
        return config;
    }


    public static void setConfigFile(String configFile) {
        Helper.configFile = configFile;
    }

    public static String extractJsonValue(String fileName, String jsonPath) {
        return JsonPath.from(readFile(fileName)).getString(jsonPath);
    }
}
