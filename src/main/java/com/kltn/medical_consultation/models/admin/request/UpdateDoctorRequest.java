package com.kltn.medical_consultation.models.admin.request;

import lombok.Data;

@Data
public class UpdateDoctorRequest {
    private Long id;
    private String name;
    private String sex;
    private String identityNumber;
    private String phoneNumber;
}
