package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.models.IMessageCode;

public enum PatientMessageCode implements IMessageCode {
    PATIENT_NOT_FOUND("patient.not.found","Not found patient",""),
    PATIENT_NOT_EXIST("patient.not.exist","Patient does not exist",""),
    PATIENT_EXIST("patient.exist","Patient has been exist",""),
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
