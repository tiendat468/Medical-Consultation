package com.kltn.medical_consultation.models.patient.response;

import com.kltn.medical_consultation.entities.database.Parent;
import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.models.patient.ParentDetail;
import com.kltn.medical_consultation.models.patient.PatientDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientResponse {
    private ParentDetail parentDetail;
    private PatientDetail patientDetail;
    private List<PatientDetail> patientDetails;

    public CreatePatientResponse(Parent parent, Patient patient) {
        this.parentDetail = new ParentDetail(parent);
        this.patientDetail = new PatientDetail(patient);
        List<PatientDetail> list = new ArrayList<>();
        if (parent.getPatients() != null) {
            List<Patient> patients = parent.getPatients();
            for (Patient p : patients) {
                if (!p.getIsDelete()){
                    list.add(new PatientDetail(p));
                }
            }
        }
        this.patientDetails = list;
    }
}
