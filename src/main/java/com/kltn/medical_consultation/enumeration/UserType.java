package com.kltn.medical_consultation.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    ADMIN(1, "ADMIN"),
    DOCTOR(2, "DOCTOR"),
    PATIENT(3, "PATIENT"),
    ;

    private final int type;
    private final String code;

    UserType(int type, String code) {
        this.type = type;
        this.code = code;
    }

    public static UserType from(int type) {
        for (UserType _type : UserType.values()) {
            if (_type.getType() == type) {
                return _type;
            }
        }

        return null;
    }

    public static UserType from(String code) {
        for (UserType _type : UserType.values()) {
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
