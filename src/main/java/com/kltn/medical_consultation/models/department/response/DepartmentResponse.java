package com.kltn.medical_consultation.models.department.response;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.Symptom;
import com.kltn.medical_consultation.models.doctor.DoctorDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentResponse {
    private Long id;
    private String name;
    private String symbol;
    private Double price = 0.0;
    private List<DoctorDTO> doctors = new ArrayList<>();
    private List<String> symptoms = new ArrayList<>();

    public static DepartmentResponse of(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setSymbol(department.getSymbol());
        response.setPrice(department.getPrice());

        List<DoctorDTO> doctorsRes = new ArrayList<>();
        List<Doctor> doctors = department.getDoctors();
        for (Doctor doctor : doctors) {
            DoctorDTO doctorDTO = DoctorDTO.of(doctor);
            doctorsRes.add(doctorDTO);
        }
        response.setDoctors(doctorsRes);

        List<String> symptomsRes = new ArrayList<>();
        List<Symptom> symptoms = department.getSymptoms();
        for (Symptom symptom : symptoms) {
            symptomsRes.add(symptom.getName());
        }
        response.setSymptoms(symptomsRes);
        return response;
    }
}
