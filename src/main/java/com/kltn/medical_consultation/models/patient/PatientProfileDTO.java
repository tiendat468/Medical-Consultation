package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.entities.database.PatientProfile;
import lombok.Data;

@Data
public class PatientProfileDTO {
    private Long patientId;
    private Long patientProfileId;
    private String name;
    private String birthday;
    private String sex;
    private String phoneNumber;
    private String symptom;
    private String diagnostic;
    private Boolean isComplete;

    public static PatientProfileDTO of(PatientProfile patientProfile) {
        PatientProfileDTO patientProfileDTO = new PatientProfileDTO();
        patientProfileDTO.setPatientId(patientProfile.getPatient().getId());
        patientProfileDTO.setPatientProfileId(patientProfile.getId());
        patientProfileDTO.setName(patientProfile.getPatient().getFullName());
        patientProfileDTO.setBirthday(patientProfile.getPatient().getBirthday());
        patientProfileDTO.setSex(patientProfile.getPatient().getSex());
        patientProfileDTO.setPhoneNumber(patientProfile.getPatient().getPhoneNumber());
        patientProfileDTO.setSymptom(patientProfile.getSymptom());
        patientProfileDTO.setDiagnostic(patientProfile.getDiagnostic());
        patientProfileDTO.setIsComplete(patientProfile.getIsComplete());
        return patientProfileDTO;
    }
}
