package com.kltn.medical_consultation.models.patient;

import lombok.Data;

@Data
public class CreatePatientProfileRequest {
    public String fullName;
    public String birthday;
    public String phoneNumber;
    public String sex;
    public String job;
    public String email;
    public String identityNumber;
    public String ethnic;
    public String address;
}
