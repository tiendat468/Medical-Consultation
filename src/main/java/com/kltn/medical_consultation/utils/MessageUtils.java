package com.kltn.medical_consultation.utils;

public class MessageUtils {

    public static String paramInvalid(String param){
        return "request param [" + param + "] is invalid";
    }

    public static String notFound(String param){
        return "not found [" + param + "]";
    }
}
