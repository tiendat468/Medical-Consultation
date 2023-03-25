package com.kltn.medical_consultation.models.auth.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String verifyCode;
    private String otp;
}
