package com.softserve.edu.util;

import java.io.InputStream;
import java.util.Properties;

public class SqlProperty {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream is = SqlProperty.class.getClassLoader().getResourceAsStream("sql.properties");
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String name) {
        if (properties.containsKey(name)) {
            return properties.getProperty(name);
        }
        throw new RuntimeException("Property key not found");
    }

}
