package com.kltn.medical_consultation.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomStringUtils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER_VN_REGEX =
            Pattern.compile("(0)+([0-9]{9})\\b", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_ADDRESS_REGEX =
            Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~`!@#$%^&*()\\\\-_+={}[\\\\]|])", Pattern.CASE_INSENSITIVE);

    public static boolean emailValidate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public static boolean phoneNumberValidate(String phoneNumberSrt) {
        Matcher matcher = VALID_PHONE_NUMBER_VN_REGEX.matcher(phoneNumberSrt);
        return matcher.find();
    }
    public static boolean passwordValidate(String passwordStr) {
        Matcher matcher = VALID_PASSWORD_ADDRESS_REGEX.matcher(passwordStr);
        return matcher.find();
    }



    public static boolean isLessLength255(String s){

        if (s.length() > 255) {
            return false;
        }
        return true;
    }

    public static boolean isLessLength(String s, Integer length){

        if (s.length() > length) {
            return false;
        }
        return true;
    }

    public static boolean isNotEmptyWithCondition(String s, Integer length){

        if (s.length() > length) {
            return false;
        }
        return true;
    }
}
