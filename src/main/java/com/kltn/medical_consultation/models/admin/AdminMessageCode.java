package com.kltn.medical_consultation.models.admin;

import com.kltn.medical_consultation.models.IMessageCode;

public enum AdminMessageCode  implements IMessageCode {
    USER_TYPE_INVALID("USER_TYPE_INVALID", "Loại người dùng không hợp lệ"),
    USER_EXIST("USER_EXIST", "Email đã tồn tại"),
    ;

    private String code;
    private String message;

    AdminMessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
