package com.kltn.medical_consultation.models.schedule;

import com.kltn.medical_consultation.models.IMessageCode;

public enum ScheduleMessageCode implements IMessageCode {
    SCHEDULE_NOT_FOUND("SCHEDULE_NOT_FOUND","Không tìm thấy lịch khám"),
    SCHEDULE_INVALID("SCHEDULE_INVALID","Lịch khám không hợp lệ"),
    SCHEDULE_IS_PAID("SCHEDULE_IS_PAID","Lịch khám đã được thanh toán"),
    SCHEDULE_NOT_BELONG("SCHEDULE_NOT_BELONG","Lịch khám không thuộc bác sĩ"),
    ;
    private String code;
    private String message;

    ScheduleMessageCode(String code, String message) {
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
