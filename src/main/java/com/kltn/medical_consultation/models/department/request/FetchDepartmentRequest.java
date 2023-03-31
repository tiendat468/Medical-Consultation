package com.kltn.medical_consultation.models.department.request;

import lombok.Data;

@Data
public class FetchDepartmentRequest {
    private Long patientProfileId;
    private String scheduleDate;
}
