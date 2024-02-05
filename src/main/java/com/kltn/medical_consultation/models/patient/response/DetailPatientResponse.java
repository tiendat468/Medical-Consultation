package com.kltn.medical_consultation.models.patient.response;

import com.kltn.medical_consultation.entities.database.Parent;
import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.models.patient.ParentDetail;
import com.kltn.medical_consultation.models.patient.PatientDetail;
import com.kltn.medical_consultation.models.patient.PatientProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailPatientResponse {
    private ParentDetail parentDetail;
    private PatientDetail patientDetail;
    private List<PatientProfileDTO> histories;

    public DetailPatientResponse(Parent parent, Patient patient) {
        this.parentDetail = new ParentDetail(parent);
        this.patientDetail = new PatientDetail(patient);
    }
}
