package com.kltn.medical_consultation.models.schedule.response;

import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import lombok.Data;

@Data
public class DetailScheduleResponse {
    private Long departmentId;
    private String departmentName;
    private Long doctorId;
    private String doctorName;
    private Long patientProfileId;
    private String symptom;
    private String medicalDate;
    private String hours;
    private Double price = 0.0;
    private Boolean isDone;
    private Boolean isPay;

    public static DetailScheduleResponse of(MedicalSchedule medicalSchedule, Department department) {
        DetailScheduleResponse response = new DetailScheduleResponse();
        response.setDepartmentId(department.getId());
        response.setDepartmentName(department.getName());
        response.setDoctorId(medicalSchedule.getDoctorId());
        response.setDoctorName(medicalSchedule.getDoctor().getFullName());
        response.setPatientProfileId(medicalSchedule.getPatientProfileId());
        response.setSymptom(medicalSchedule.getPatientProfile().getSymptom());
        response.setMedicalDate(medicalSchedule.getMedicalDate());
        response.setHours(medicalSchedule.getHours());
        response.setPrice(medicalSchedule.getPrice());
        response.setIsDone(medicalSchedule.getIsDone());
        response.setIsPay(medicalSchedule.getIsPay());
        return response;
    }

    public static DetailScheduleResponse of(MedicalSchedule medicalSchedule) {
        DetailScheduleResponse response = new DetailScheduleResponse();
        response.setDepartmentId(medicalSchedule.getDoctor().getDepartment().getId());
        response.setDepartmentName(medicalSchedule.getDoctor().getDepartment().getName());
        response.setDoctorId(medicalSchedule.getDoctorId());
        response.setDoctorName(medicalSchedule.getDoctor().getFullName());
        response.setPatientProfileId(medicalSchedule.getPatientProfileId());
        response.setSymptom(medicalSchedule.getPatientProfile().getSymptom());
        response.setMedicalDate(medicalSchedule.getMedicalDate());
        response.setHours(medicalSchedule.getHours());
        response.setPrice(medicalSchedule.getPrice());
        response.setIsDone(medicalSchedule.getIsDone());
        response.setIsPay(medicalSchedule.getIsPay());
        return response;
    }
}
