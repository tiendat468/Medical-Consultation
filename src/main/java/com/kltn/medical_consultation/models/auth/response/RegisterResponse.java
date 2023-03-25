package com.kltn.medical_consultation.models.auth.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private String verifyCode;
    private String expiredAt;
}
