package com.kltn.medical_consultation.models.auth;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String name;
    private String email;
    private String phone;
    private String type;
}
