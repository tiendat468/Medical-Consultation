package com.kltn.medical_consultation.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActivityType {
    VERIFY_MAIL(1, "VERIFY_MAIL"),
    FORGOT_PASSWORD(2, "FORGOT_PASSWORD"),
    ;

    private final int type;
    private final String code;

    ActivityType(int type, String code) {
        this.type = type;
        this.code = code;
    }

    public static ActivityType from(int type) {
        for (ActivityType _type : ActivityType.values()) {
            if (_type.getType() == type) {
                return _type;
            }
        }

        return null;
    }

    public static ActivityType from(String code) {
        for (ActivityType _type : ActivityType.values()) {
            if (_type.getCode().equals(code)) {
                return _type;
            }
        }

        return null;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }
}
