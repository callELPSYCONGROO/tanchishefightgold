package com.wuhenjian.tanchishe_fight_gold.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author SwordNoTrace
 * @date 2017/8/17 23:27
 */
public class PropertiesUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertiesUtil() {}

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static Integer getInteger(String key) throws NumberFormatException {
        return Integer.valueOf(properties.getProperty(key));
    }

    public static Long getLong(String key) throws NumberFormatException {
        return Long.valueOf(properties.getProperty(key));
    }

    public static Double getDouble(String key) throws NumberFormatException {
        return Double.valueOf(properties.getProperty(key));
    }

    public static Date getDate(String key, String pattern) throws ParseException {
        String formatter = pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern;
        String date = properties.getProperty(key);
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.parse(date);
    }
}
