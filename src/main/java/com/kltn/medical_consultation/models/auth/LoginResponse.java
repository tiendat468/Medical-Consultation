package com.kltn.medical_consultation.models.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String expiredAt;
}
