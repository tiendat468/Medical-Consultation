package com.kltn.medical_consultation.models.doctor.response;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import lombok.Data;

@Data
public class DoctorScheduleResponse {
    private Long scheduleId;
    private Long doctorId;
    private Long patientProfileId;
    private String medicalDate;
    private String hours;
    private Boolean isDone;
    private Boolean isPay;

    public static DoctorScheduleResponse of(MedicalSchedule medicalSchedule) {
        DoctorScheduleResponse response = new DoctorScheduleResponse();
        response.setDoctorId(medicalSchedule.getDoctorId());
        response.setPatientProfileId(medicalSchedule.getPatientProfileId());
        response.setScheduleId(medicalSchedule.getId());
        response.setMedicalDate(medicalSchedule.getMedicalDate());
        response.setHours(medicalSchedule.getHours());
        response.setIsDone(medicalSchedule.getIsDone());
        response.setIsPay(medicalSchedule.getIsPay());
        return response;
    }
}
