package com.kltn.medical_consultation.models.schedule.request;

import lombok.Data;

@Data
public class FetchDepartmentRequest {
    private Long patientProfileId;
    private String scheduleDate;
}
