package com.kltn.medical_consultation.models.doctor.request;

import com.kltn.medical_consultation.models.doctor.MedicineDTO;
import lombok.Data;

import java.util.List;

@Data
public class MedicalPatientRequest {
    private Long patientId;
    private String anamnesis;
    private String symptom;
    private String diagnostic;
    private String advice;
    private List<MedicineDTO> medicines;
}
