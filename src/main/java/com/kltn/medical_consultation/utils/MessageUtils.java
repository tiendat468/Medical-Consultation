package com.kltn.medical_consultation.utils;

public class MessageUtils {

    public static String paramRequired(String param){
        return "[" + param + "] is required";
    }

    public static String notFound(String param){
        return "Not found [" + param + "]";
    }

    public static String notExist(String param){
        return "[" + param + "] does not exist";
    }

    public static String notActivate(String param){
        return "[" + param + "] not activated";
    }
    public static String wrongFormatPhoneNumber(String param){
        return "[" + param + "] format is wrong  - Ex: 0123456789";
    }
    public static String wrongFormatEmail(String param){
        return "[" + param + "] format is wrong  - Ex: example@gmail.com";
    }

    public static String outOfRange(String param, Integer length){
        return "limit length of [" + param + "] is " + length;
    }
}
