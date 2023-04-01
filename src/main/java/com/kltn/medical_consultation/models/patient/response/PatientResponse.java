package com.kltn.medical_consultation.models.patient.response;

import com.kltn.medical_consultation.entities.database.Patient;
import lombok.Data;

@Data
public class PatientResponse {
    private Long id;
    private Long userId;
    private String fullName;
    private String birthday;
    private String phone;
    private String sex;
    private String address;
    private String job;
    private String identityNumber;

    public static PatientResponse of(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setUserId(patient.getUserId());
        response.setFullName(patient.getFullName());
        response.setBirthday(patient.getBirthday());
        response.setSex(patient.getSex());
        response.setJob(patient.getJob());
        response.setIdentityNumber(patient.getIdentityNumber());
        response.setAddress(patient.getAddress());
        response.setPhone(patient.getPhoneNumber());
        return response;
    }
}
