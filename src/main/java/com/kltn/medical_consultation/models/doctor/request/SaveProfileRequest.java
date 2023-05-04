package com.kltn.medical_consultation.models.doctor.request;

import lombok.Data;

@Data
public class SaveProfileRequest {
    private Long id;
    private String fullName;
    private String sex;
    private String identityNumber;
    private String phoneNumber;
}
