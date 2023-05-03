package com.kltn.medical_consultation.models.doctor.request;

import lombok.Data;

@Data
public class UpdateScheduleRequest {
    private Long scheduleId;
    private String diagnostic;
    private boolean isPay = false;
}
