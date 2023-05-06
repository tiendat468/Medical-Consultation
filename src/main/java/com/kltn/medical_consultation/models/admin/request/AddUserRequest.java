package com.kltn.medical_consultation.models.admin.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String name;
    private String email;
    private String sex;
    private String identityNumber;
    private String phoneNumber;
    private int type;
    private Long departmentId;
}
