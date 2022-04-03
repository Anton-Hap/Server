package utils;

import constants.Data;

import java.io.FileReader;
import java.util.Properties;

public abstract class ReadConfigUtils {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(new FileReader(Data.CONFIG_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties getProperties() {
        return properties;
    }

    public static String getValue(String key) {
        return (String) getProperties().getProperty(key);
    }
}
