package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.entities.database.ProfileMedicine;
import com.kltn.medical_consultation.models.doctor.MedicineDTO;
import com.kltn.medical_consultation.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfileDTO {
    private Long patientId;
    private Long patientProfileId;
    private String createdAt;
    private String symptom;
    private String anamnesis;
    private String advice;
    private String diagnostic;
    private List<MedicineDTO> medicines;

    public static PatientProfileDTO of(PatientProfile patientProfile) {
        PatientProfileDTO patientProfileDTO = new PatientProfileDTO();
        patientProfileDTO.setPatientId(patientProfile.getPatient().getId());
        patientProfileDTO.setPatientProfileId(patientProfile.getId());
        patientProfileDTO.setSymptom(patientProfile.getSymptom());
        patientProfileDTO.setDiagnostic(patientProfile.getDiagnostic());
        patientProfileDTO.setAdvice(patientProfile.getAdvice());
        patientProfileDTO.setAnamnesis(patientProfile.getAnamnesis());
        return patientProfileDTO;
    }

    public PatientProfileDTO(PatientProfile patientProfile) {
        this.patientId = patientProfile.getPatient().getId();
        this.patientProfileId = patientProfile.getId();
        this.createdAt = TimeUtils.dateToStringSimpleDateFormat(Date.from(patientProfile.getCreatedAt().toInstant()));
        this.symptom = patientProfile.getSymptom();
        this.anamnesis = patientProfile.getAnamnesis();
        this.advice = patientProfile.getAdvice();
        this.diagnostic = patientProfile.getDiagnostic();
        List<MedicineDTO> medicineDTOs = new ArrayList<>();
        List<ProfileMedicine> medicines = patientProfile.getMedicines();
        for (ProfileMedicine medicine : medicines) {
            if (!medicine.getIsDelete()) {
                medicineDTOs.add(new MedicineDTO(medicine));
            }
        }

        this.medicines = medicineDTOs;
    }

    public PatientProfileDTO(PatientProfile patientProfile, List<ProfileMedicine> profileMedicines) {
        this.patientId = patientProfile.getPatient().getId();
        this.patientProfileId = patientProfile.getId();
        this.createdAt = TimeUtils.dateToStringSimpleDateFormat(Date.from(patientProfile.getCreatedAt().toInstant()));
        this.symptom = patientProfile.getSymptom();
        this.anamnesis = patientProfile.getAnamnesis();
        this.advice = patientProfile.getAdvice();
        this.diagnostic = patientProfile.getDiagnostic();
        List<MedicineDTO> medicineDTOs = new ArrayList<>();
        for (ProfileMedicine medicine : profileMedicines) {
            if (!medicine.getIsDelete()) {
                medicineDTOs.add(new MedicineDTO(medicine));
            }
        }

        this.medicines = medicineDTOs;
    }
}
