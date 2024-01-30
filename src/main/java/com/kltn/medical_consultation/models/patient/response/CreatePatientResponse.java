package com.kltn.medical_consultation.models.patient.response;

import com.kltn.medical_consultation.entities.database.Parent;
import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.models.patient.ParentDetail;
import com.kltn.medical_consultation.models.patient.PatientDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientResponse {
    private ParentDetail parentDetail;
    private PatientDetail patientDetail;

    public CreatePatientResponse(Parent parent, Patient patient) {
        this.parentDetail = new ParentDetail(parent);
        this.patientDetail = new PatientDetail(patient);
    }
}