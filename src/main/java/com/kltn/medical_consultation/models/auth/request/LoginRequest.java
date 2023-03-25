package com.kltn.medical_consultation.models.auth.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private Boolean loginWithDoctor;
}
