package org.zendesk.testing;

import com.google.common.io.Resources;
import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 * Utility class
 */
public class Helper {
    private static Properties config;

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


    public static Properties getConfig() {
        if (config == null) {
            config = new Properties();
            InputStream is = Helper.getResourceAsStream("/zendesk-test.properties");
            try {
                config.load(is);
            } catch (IOException e) {
                throw new RuntimeException("can't load properties file" + e);
            }
        }
        return config;
    }

    public static String getProperty(String prop) {
        return getConfig().getProperty(prop);
    }
}
