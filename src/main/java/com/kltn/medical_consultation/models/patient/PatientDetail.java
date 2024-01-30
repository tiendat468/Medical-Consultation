package com.kltn.medical_consultation.models.patient;

import com.kltn.medical_consultation.entities.database.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetail {
    private Long id;
    private String fullName;
    private String birthday;
    private String sex;
    private Long weight;

    public PatientDetail(Patient patient) {
        this.id = patient.getId();
        this.fullName = patient.getFullName();
        this.birthday = patient.getBirthday();
        this.sex = patient.getSex();
        this.weight = patient.getWeight();
    }
}
