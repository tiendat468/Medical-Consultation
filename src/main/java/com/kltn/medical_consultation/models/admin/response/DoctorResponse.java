package com.kltn.medical_consultation.models.admin.response;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.models.auth.UserDTO;
import com.kltn.medical_consultation.models.doctor.DoctorDTO;
import lombok.Data;

@Data
public class DoctorResponse {
    private DoctorDTO doctor;
    private UserDTO user;

    public static DoctorResponse of(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setDoctor(new DoctorDTO(doctor));
        response.setUser(new UserDTO(doctor.getUser()));
        return response;
    }

    public static DoctorResponse of(Doctor doctor, User user) {
        DoctorResponse response = new DoctorResponse();
        response.setDoctor(new DoctorDTO(doctor));
        response.setUser(new UserDTO(user));
        return response;
    }
}
