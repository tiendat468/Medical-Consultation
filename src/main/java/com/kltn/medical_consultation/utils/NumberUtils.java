package com.kltn.medical_consultation.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class NumberUtils {
    private static final Logger LOGGER = LogManager.getLogger(NumberUtils.class);
    public static Long getLongFromMap(Map<String, Object> data , String key) {

        Object value = data.get(key);

        if (value == null)
            return null;

        if (value instanceof Long)
            return (Long) value;

        if (value instanceof String){
            try {
                return Long.valueOf((String) value);

            }catch (Exception e){
                return 0L;
            }
        }


        if (value instanceof Integer)
            return ((Integer) value ).longValue();

        return 0L;
    }

    public static Long getLongFromMap(Map<String, Object> data , String key,Long defaultValue) {

        Object value = data.get(key);

        if (value == null)
            return defaultValue;

        if (value instanceof Long)
            return (Long) value;

        if (value instanceof String){
            try {
                return Long.valueOf((String) value);

            }catch (Exception e){
                return defaultValue;
            }
        }


        if (value instanceof Integer)
            return ((Integer) value ).longValue();

        return defaultValue;
    }

    public static Integer getIntegerFromMap(Map<String, Object> data , String key) {

        Object value = data.get(key);

        if (value == null)
            return null;

        if (value instanceof Integer)
            return (Integer) value ;

        if (value instanceof Long)
            return ((Long) value).intValue();

        if (value instanceof String){
            try {
                return Integer.valueOf((String) value);

            }catch (Exception e){
                return null;
            }
        }


        return null;
    }

    public static Integer getIntegerFromMap(Map<String, Object> data , String key, Integer defaultValue) {

        Object value = data.get(key);

        if (value == null)
            return defaultValue;

        if (value instanceof Integer)
            return (Integer) value ;

        if (value instanceof Long)
            return ((Long) value).intValue();

        if (value instanceof String){
            try {
                return Integer.valueOf((String) value);

            }catch (Exception e){
                return defaultValue;
            }
        }


        return defaultValue;
    }



    public static Long getValueFromMap(Map<String, Object> data, String key) {

  
        Object value = data.get(key);
        if (value instanceof Integer) {
            Long result = ((Integer) value).longValue();
            return result;
        } else if (value instanceof Long) {
            return (Long) value;
        }

        return null;


    }

    public static Integer getIntValueFromMap(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Integer) {
            Long result = ((Integer) value).longValue();

            return (Integer) value;
        }

        return null;


    }

    public static Integer getNumberFromMap(Map<String, String> data, String key, Integer defaultValue) {
        String number = data.get(key);
        try {
            if (!StringUtils.isEmpty(number)) {
                return Integer.parseInt(number);
            }
        } catch (Exception e) {
            LOGGER.error("get number exception ", e);
        }

        return defaultValue;
    }

    public static Long getNumberFromMap(Map<String, String> data, String key, Long defaultValue) {
        String number = data.get(key);
        try {
            if (!StringUtils.isEmpty(number)) {
                return Long.parseLong(number);
            }
        } catch (Exception e) {
            LOGGER.error("get number exception {}", number, e);
        }

        return defaultValue;
    }

    public static Long getLongFromMapObject(Map<String, Object> data, String key, Long defaultValue) {
        Object number = data.get(key);
        if (number == null) return defaultValue;
        try {

            if (number instanceof Long) return (Long) number;
            if (number instanceof Integer) return ((Integer) number).longValue();
        } catch (Exception e) {
            LOGGER.error("get number exception {}", number, e);
        }

        return defaultValue;
    }

    public static Integer getIntegerFromMapObject(Map<String, Object> data, String key, Integer defaultValue) {
        Object number = data.get(key);
        if (number == null) return defaultValue;
        try {
            if (number instanceof Integer) return (Integer) number;
        } catch (Exception e) {
            LOGGER.error("get number exception {}", number, e);
        }

        return defaultValue;
    }

    public static Short getNumberFromMap(Map<String, String> data, String key, Short defaultValue) {
        String number = data.get(key);
        try {
            if (!StringUtils.isEmpty(number)) {
                return Short.parseShort(number);
            }
        } catch (Exception e) {
            LOGGER.error("get number exception {}", number, e);
        }

        return defaultValue;
    }


}
