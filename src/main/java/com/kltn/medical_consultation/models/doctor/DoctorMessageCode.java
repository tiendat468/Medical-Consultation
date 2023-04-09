package com.kltn.medical_consultation.models.doctor;

import com.kltn.medical_consultation.models.IMessageCode;

public enum DoctorMessageCode implements IMessageCode {
    DOCTOR_INVALID("DOCTOR_INVALID","Bác sĩ không hợp lệ","Bác sĩ không hợp lệ"),
    DOCTOR_NOT_FOUND("DOCTOR_NOT_FOUND","Không tìm thấy bác sĩ","Không tìm thấy bác sĩ"),
    DOCTOR_NOT_EXIST("DOCTOR_NOT_EXIST","Bác sĩ không tồn tại","Bác sĩ không tồn tại"),
    DOCTOR_EXIST("DOCTOR_EXIST","Bác sĩ đã tồn tại","Bác sĩ đã tồn tại"),
    ;
    private String code;
    private String message;
    private String instance;

    DoctorMessageCode(String code, String message, String instance) {
        this.code = code;
        this.message = message;
        this.instance = instance;
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
