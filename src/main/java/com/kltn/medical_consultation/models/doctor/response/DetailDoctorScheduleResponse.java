package com.kltn.medical_consultation.models.doctor.response;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.doctor.PatientProfileDTO;
import lombok.Data;

@Data
public class DetailDoctorScheduleResponse {
    private Long scheduleId;
    private Long doctorId;
    private String medicalDate;
    private String hours;
    private Double price = 0.0;
    private Boolean isDone;
    private Boolean isPay;
    private PatientProfileDTO patientProfile;

    public static DetailDoctorScheduleResponse of(MedicalSchedule medicalSchedule, PatientProfileDTO patientProfileDTO) {
        DetailDoctorScheduleResponse response = new DetailDoctorScheduleResponse();
        response.setDoctorId(medicalSchedule.getDoctorId());
        response.setScheduleId(medicalSchedule.getId());
        response.setMedicalDate(medicalSchedule.getMedicalDate());
        response.setHours(medicalSchedule.getHours());
        response.setPrice(medicalSchedule.getPrice());
        response.setIsDone(medicalSchedule.getIsDone());
        response.setIsPay(medicalSchedule.getIsPay());
        response.setPatientProfile(patientProfileDTO);
        return response;
    }
}
