package com.kltn.medical_consultation.models.auth;

import lombok.Data;

@Data
public class CheckOtpRequest {
    private String email;
    private String otp;
}
