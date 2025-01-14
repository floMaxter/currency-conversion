package com.projects.currencyconversion.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesConnectionUtil {

    private static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesConnectionUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesConnectionUtil.class.getClassLoader()
                .getResourceAsStream(APPLICATION_PROPERTIES_FILE_NAME)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
