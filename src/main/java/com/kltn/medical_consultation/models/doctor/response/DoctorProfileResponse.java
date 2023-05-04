package com.kltn.medical_consultation.models.doctor.response;

import com.kltn.medical_consultation.entities.database.*;
import com.kltn.medical_consultation.models.department.DepartmentDTO;
import com.kltn.medical_consultation.models.doctor.AcademicRankDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DoctorProfileResponse {
    private Long doctorId;
    private String fullName;
    private String sex;
    private String identityNumber;
    private String phoneNumber;
    private Long userId;
    private DepartmentDTO department;
    private List<AcademicRankDTO> academicRanks;

    public static DoctorProfileResponse of(Doctor doctor) {
        DoctorProfileResponse response = new DoctorProfileResponse();
        response.setDoctorId(doctor.getId());
        response.setFullName(doctor.getFullName());
        response.setSex(doctor.getSex());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setIdentityNumber(doctor.getIdentityNumber());
        response.setUserId(doctor.getUserId());
        response.setDepartment(new DepartmentDTO(doctor.getDepartment()));

        List<AcademicRankDTO> academicRanks = new ArrayList<>();
        List<DoctorRank> doctorRanks = new ArrayList<>(doctor.getDoctorRanks());
        for (DoctorRank doctorRank : doctorRanks) {
            AcademicRankDTO academicRankDTO = AcademicRankDTO.of(doctorRank.getAcademicRank());
            academicRanks.add(academicRankDTO);
        }
        response.setAcademicRanks(academicRanks);

        return response;
    }
}
