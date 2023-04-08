package com.kltn.medical_consultation.models.patient.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private Long profileId;
    private String diagnostic;
}
