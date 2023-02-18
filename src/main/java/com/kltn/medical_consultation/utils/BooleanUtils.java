package com.kltn.medical_consultation.utils;

public class BooleanUtils {
    public static boolean safeUnboxing(Boolean value){
        if (value == null){
            return false;
        }
        return value;
    }
}
