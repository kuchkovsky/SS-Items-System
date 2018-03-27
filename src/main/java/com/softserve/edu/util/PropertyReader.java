package com.softserve.edu.util;

import com.softserve.edu.exception.PropertyNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class PropertyReader {

    private Properties properties = new Properties();

    public PropertyReader(String propertyFileName) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName);
            if (is == null) {
                throw new IOException("Failed to load property file: " + propertyFileName);
            }
            properties.load(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public String get(String name) {
        if (!containsKey(name)) {
            throw new PropertyNotFoundException("Could not find property by key: " + name);
        }
        return properties.getProperty(name);
    }

    public boolean containsKey(String name) {
        return properties.containsKey(name);
    }

}
