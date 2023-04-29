package com.kltn.medical_consultation.models.admin.request;

import lombok.Data;

@Data
public class SaveUserRequest {
    private Long id;
    private String name;
    private String email;
    private int type;

}
