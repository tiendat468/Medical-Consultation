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
public class SearchPatientResponse {
    private ParentDetail parentDetail;
    private List<PatientDetail> patientDetails;

    public SearchPatientResponse(Parent parent) {
        this.parentDetail = new ParentDetail(parent);

        List<PatientDetail> list = new ArrayList<>();
        List<Patient> patients = parent.getPatients();
        for (Patient patient : patients) {
            if (!patient.getIsDelete()){
                list.add(new PatientDetail(patient));
            }
        }
        this.patientDetails = list;
    }
}
