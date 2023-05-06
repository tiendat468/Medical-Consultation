package com.kltn.medical_consultation.models.admin.response;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.models.auth.UserDTO;
import com.kltn.medical_consultation.models.doctor.DoctorDTO;
import com.kltn.medical_consultation.models.patient.PatientDTO;
import lombok.Data;

@Data
public class PatientResponse {
    private PatientDTO patient;
    private UserDTO user;

    public static PatientResponse of(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setPatient(new PatientDTO(patient));
        response.setUser(new UserDTO(patient.getUser()));
        return response;
    }

    public static PatientResponse of(Patient patient, User user) {
        PatientResponse response = new PatientResponse();
        response.setPatient(new PatientDTO(patient));
        response.setUser(new UserDTO(user));
        return response;
    }
}
