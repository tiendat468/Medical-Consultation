package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.entities.database.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientRequest {
    private String phoneNumber;
    private String fullName;
    private String address;
    private PatientDetail patientDetail;
}
