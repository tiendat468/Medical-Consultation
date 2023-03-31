package com.kltn.medical_consultation.models.schedule.request;

import lombok.Data;

@Data
public class SaveScheduleRequest {
    private Long departmentId;
    private Long doctorId;
    private Long patientProfileId;
    private String medicalDate;
    private String hours;
}
