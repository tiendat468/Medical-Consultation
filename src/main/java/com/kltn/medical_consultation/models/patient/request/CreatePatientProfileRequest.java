package com.kltn.medical_consultation.models.patient.request;

import lombok.Data;

@Data
public class CreatePatientProfileRequest {
    public String scheduleDate;
    public String symptom;
}
