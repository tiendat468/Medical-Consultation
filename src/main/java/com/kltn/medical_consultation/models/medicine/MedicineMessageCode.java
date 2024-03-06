package com.kltn.medical_consultation.models.medicine;

import com.kltn.medical_consultation.models.IMessageCode;

public enum MedicineMessageCode implements IMessageCode {
    MEDICINE_NOT_FOUND("MEDICINE_NOT_FOUND","Không tìm thấy thuốc"),
    ;
    private String code;
    private String message;
    MedicineMessageCode(String code, String message) {
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
