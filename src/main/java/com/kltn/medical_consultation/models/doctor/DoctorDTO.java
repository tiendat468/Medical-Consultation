package com.kltn.medical_consultation.models.doctor;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.models.department.DepartmentDTO;
import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String sex;
    private String identityNumber;
    private String phoneNumber;
    private DepartmentDTO department;

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.fullName = doctor.getFullName();
        this.sex = doctor.getSex();
        this.identityNumber = doctor.getIdentityNumber();
        this.phoneNumber = doctor.getPhoneNumber();
        this.department = new DepartmentDTO(doctor.getDepartment());
    }
}
