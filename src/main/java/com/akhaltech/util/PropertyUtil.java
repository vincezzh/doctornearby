package com.akhaltech.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by vince on 2015-10-15.
 */
public class PropertyUtil {

    private Properties properties;

    public PropertyUtil() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
