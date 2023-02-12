package com.kltn.medical_consultation.utils;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email))
            return false;
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }




}
