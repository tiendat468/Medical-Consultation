package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalScheduleRepository extends JpaRepository<MedicalSchedule, Long> {
    List<MedicalSchedule> findByDoctorIdAndMedicalDate(Long doctorId, String medicalDate);

    List<MedicalSchedule> findByPatientProfileId(Long patientProfileId);
}