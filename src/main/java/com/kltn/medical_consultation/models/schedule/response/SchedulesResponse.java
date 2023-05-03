package com.kltn.medical_consultation.models.schedule.response;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.doctor.DoctorDTO;
import com.kltn.medical_consultation.models.patient.PatientProfileDTO;
import lombok.Data;

@Data
public class SchedulesResponse {
    private Long scheduleId;
    private String medicalDate;
    private String hours;
    private Double price = 0.0;
    private Boolean isDone;
    private Boolean isPay;
    private Long doctorId;
    private DoctorDTO doctor;
    private Long patientProfileId;
    private PatientProfileDTO patientProfile;

    public static SchedulesResponse of(MedicalSchedule medicalSchedule) {
        SchedulesResponse response = new SchedulesResponse();
        response.setDoctorId(medicalSchedule.getDoctorId());
        response.setPatientProfileId(medicalSchedule.getPatientProfileId());
        response.setScheduleId(medicalSchedule.getId());
        response.setMedicalDate(medicalSchedule.getMedicalDate());
        response.setHours(medicalSchedule.getHours());
        response.setIsDone(medicalSchedule.getIsDone());
        response.setIsPay(medicalSchedule.getIsPay());
        response.setPatientProfile(PatientProfileDTO.of(medicalSchedule.getPatientProfile()));
        response.setDoctor(new DoctorDTO(medicalSchedule.getDoctor()));
        response.setPrice(medicalSchedule.getPrice());
        return response;
    }
}
