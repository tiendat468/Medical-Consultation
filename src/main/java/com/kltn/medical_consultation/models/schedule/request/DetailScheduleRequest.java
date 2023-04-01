package com.kltn.medical_consultation.models.schedule.request;

import lombok.Data;

@Data
public class DetailScheduleRequest {
    private Long patientProfileId;
    private Long scheduleId;
}
