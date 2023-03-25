package com.kltn.medical_consultation.utils;

public class MessageUtils {

    public static String paramInvalid(String param){
        return "Request param [" + param + "] is invalid";
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
}
