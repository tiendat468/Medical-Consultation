package com.kltn.medical_consultation.models.admin.request;

import lombok.Data;

@Data
public class ActivateUserRequest {
    private Long userId;
    private Boolean isActive = false;
}
