package com.kltn.medical_consultation.models.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
