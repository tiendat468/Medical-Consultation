package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.entities.database.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long id;
    private String fullName;
    private String birthday;
    private String phoneNumber;
    private String sex;
    private String address;
    private String job;
    private String identityNumber;

    public static PatientDTO of(Patient patient) {
        PatientDTO response = new PatientDTO();
        response.setId(patient.getId());
        response.setFullName(patient.getFullName());
        response.setBirthday(patient.getBirthday());
        response.setSex(patient.getSex());
        response.setJob(patient.getJob());
        response.setIdentityNumber(patient.getIdentityNumber());
        response.setAddress(patient.getAddress());
        response.setPhoneNumber(patient.getPhoneNumber());
        return response;
    }

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.fullName = patient.getFullName();
        this.birthday = patient.getBirthday();
        this.phoneNumber = patient.getPhoneNumber();
        this.sex = patient.getSex();
        this.address = patient.getAddress();
        this.job = patient.getJob();
        this.identityNumber = patient.getIdentityNumber();
    }
}
