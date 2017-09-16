package org.zendesk.testing;

import com.google.common.io.Resources;
import org.apache.commons.codec.Charsets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.common.io.Files;

/**
 * Utility class
 */
public class Helper {
    public static String readFile(String path) {
        String json = null;
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
}
