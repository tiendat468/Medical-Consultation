package com.kltn.medical_consultation.models.patient.response;

import com.kltn.medical_consultation.entities.database.PatientProfile;
import lombok.Data;

@Data
public class PatientProfileResponse {
    private Long id;
    private Long patientId;
    private String symptom;
    private String diagnostic;
    private Boolean isComplete;

    public static PatientProfileResponse of(PatientProfile patientProfile) {
        PatientProfileResponse response = new PatientProfileResponse();
        response.setId(patientProfile.getId());
        response.setPatientId(patientProfile.getPatient().getId());
        response.setSymptom(patientProfile.getSymptom());
        response.setDiagnostic(patientProfile.getDiagnostic());
        response.setIsComplete(patientProfile.getIsComplete());

        return response;
    }
}
