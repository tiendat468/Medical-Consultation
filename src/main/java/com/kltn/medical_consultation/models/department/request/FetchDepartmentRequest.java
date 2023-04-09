package com.kltn.medical_consultation.models.department.request;

import lombok.Data;

@Data
public class FetchDepartmentRequest {
    private String symptom;
    private String medicalDate;
}
