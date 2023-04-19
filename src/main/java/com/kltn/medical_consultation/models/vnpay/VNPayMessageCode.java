package com.kltn.medical_consultation.models.vnpay;

import com.kltn.medical_consultation.models.IMessageCode;

public enum VNPayMessageCode implements IMessageCode {
    ERROR_PAYMENT_NOT_FOUND("ERROR_PAYMENT_NOT_FOUND", "Không tìm thấy giao dịch"),
    ERROR_CREATE_PAYMENT("ERROR_CREATE_PAYMENT", "Lỗi khởi tạo giao dịch"),
    ERROR_UPDATE_PAYMENT("ERROR_UPDATE_PAYMENT", "Lỗi cập nhập giao dịch"),
    ERROR_GET_PAYMENT("ERROR_GET_PAYMENT", "Không thể lấy thông tin giao dịch"),
    ;
    private String code;
    private String message;

    VNPayMessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
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
