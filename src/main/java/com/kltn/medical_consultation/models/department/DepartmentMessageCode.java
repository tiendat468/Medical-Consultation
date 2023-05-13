package com.kltn.medical_consultation.models.department;

import com.kltn.medical_consultation.models.IMessageCode;

public enum DepartmentMessageCode implements IMessageCode {
    DEPARTMENT_NOT_FOUND("DEPARTMENT_NOT_FOUND","Không tìm thấy khoa"),
    DOCTOR_NOT_FOUND("DOCTOR_NOT_FOUND","Không tìm thấy bác sĩ"),
    DOCTOR_NOT_BELONG("DOCTOR_NOT_BELONG","Bác sĩ không thuộc khoa"),
    DOCTOR_BUSY("DOCTOR_BUSY","Bác sĩ đã có lịch khám"),
    EMPTY_SYMPTOM("EMPTY_SYMPTOM","Không tìm thấy khoa phù hợp với các triệu chứng đã nhập."),
    ;
    private String code;
    private String message;

    DepartmentMessageCode(String code, String message) {
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
