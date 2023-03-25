package com.kltn.medical_consultation.models.auth.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String expiredAt;
}
