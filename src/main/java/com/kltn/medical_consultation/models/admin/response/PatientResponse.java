package com.kltn.medical_consultation.models.admin.response;

import com.kltn.medical_consultation.entities.database.Patient;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.entities.database.User;
import com.kltn.medical_consultation.models.auth.UserDTO;
import com.kltn.medical_consultation.models.patient.PatientDTO;
import com.kltn.medical_consultation.models.patient.PatientProfileDTO;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class PatientResponse {
    private PatientDTO patient;
    private UserDTO user;
    private List<PatientProfileDTO> patientProfiles;

    public static PatientResponse of(Patient patient) {
        PatientResponse response = new PatientResponse();
        List<PatientProfileDTO> profileDTOs = new ArrayList<>();
        response.setPatient(new PatientDTO(patient));
        response.setUser(new UserDTO(patient.getUser()));

        List<PatientProfile> profiles = patient.getPatientProfiles();
        for (PatientProfile patientProfile : profiles) {
            PatientProfileDTO profileDTO = PatientProfileDTO.of(patientProfile);
            profileDTOs.add(profileDTO);
        }

        response.setPatientProfiles(profileDTOs);
        return response;
    }

    public static PatientResponse of(Patient patient, User user) {
        PatientResponse response = new PatientResponse();
        response.setPatient(new PatientDTO(patient));
        response.setUser(new UserDTO(user));

        List<PatientProfileDTO> profileDTOs = new ArrayList<>();
        List<PatientProfile> profiles = patient.getPatientProfiles();
        if (!CollectionUtils.isEmpty(profiles)) {
            for (PatientProfile patientProfile : profiles) {
                PatientProfileDTO profileDTO = PatientProfileDTO.of(patientProfile);
                profileDTOs.add(profileDTO);
            }
        }

        response.setPatientProfiles(profileDTOs);
        return response;
    }
}
