package com.kltn.medical_consultation.models.auth.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String code;
    private String password;
}
