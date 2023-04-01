package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.models.IMessageCode;

public enum PatientMessageCode implements IMessageCode {
    PATIENT_INVALID("PATIENT_INVALID","Bệnh nhân không hợp lệ","Bệnh nhân không hợp lệ"),
    PATIENT_NOT_FOUND("PATIENT_NOT_FOUND","Không tìm thấy bệnh nhân","Không tìm thấy thông tin bệnh nhân"),
    PATIENT_NOT_EXIST("PATIENT_NOT_EXIST","Bệnh nhân không tồn tại","Bệnh nhân không tồn tại"),
    PATIENT_EXIST("PATIENT_EXIST","Bệnh nhân đã tồn tại","Bệnh nhân đã tồn tại"),
    PATIENT_PROFILE_INVALID("PATIENT_PROFILE_INVALID","Hồ sơ bệnh nhân không hợp lệ","Hồ sơ bệnh nhân không hợp lệ"),
    PATIENT_PROFILE_NOT_FOUND("PATIENT_PROFILE_NOT_FOUND","Không tìm thấy thông tin bệnh nhân","Không tìm thấy thông tin bệnh nhân"),
    PROFILE_NOT_BELONG_PATIENT("PROFILE_NOT_BELONG_PATIENT","Hồ sơ bệnh nhân không thuộc về bệnh nhân","Hồ sơ bệnh nhân không thuộc về bệnh nhân"),
    ;
    private String code;
    private String message;
    private String instance;

    PatientMessageCode(String code, String message, String instance) {
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
