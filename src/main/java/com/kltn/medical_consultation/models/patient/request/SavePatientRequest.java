package com.kltn.medical_consultation.models.patient.request;

import lombok.Data;

@Data
public class SavePatientRequest {

    private String fullName;
    private String birthday;
    private String sex;
    private String address;
    private String job;
    private String identityNumber;
}
