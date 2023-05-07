package com.kltn.medical_consultation.models.doctor;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.models.department.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String sex;
    private String identityNumber;
    private String phoneNumber;
    private DepartmentDTO department;

    public static DoctorDTO of(Doctor doctor) {
        DoctorDTO response = new DoctorDTO();
        response.setId(doctor.getId());
        response.setFullName(doctor.getFullName());
        response.setSex(doctor.getSex());
        response.setIdentityNumber(doctor.getIdentityNumber());
        response.setPhoneNumber(doctor.getPhoneNumber());
        return response;
    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.fullName = doctor.getFullName();
        this.sex = doctor.getSex();
        this.identityNumber = doctor.getIdentityNumber();
        this.phoneNumber = doctor.getPhoneNumber();
        this.department = new DepartmentDTO(doctor.getDepartment());
    }
}
