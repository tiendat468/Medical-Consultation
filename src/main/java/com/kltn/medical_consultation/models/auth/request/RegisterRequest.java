package com.kltn.medical_consultation.models.auth.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String birthday;
    private String sex;
}
