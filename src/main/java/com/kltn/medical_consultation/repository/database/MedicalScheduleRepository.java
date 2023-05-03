package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalScheduleRepository extends JpaRepository<MedicalSchedule, Long>, JpaSpecificationExecutor<MedicalSchedule> {
    Optional<MedicalSchedule> findByPayCode(String payCode);
    List<MedicalSchedule> findByDoctorIdAndMedicalDateAndIsDeleteFalse(Long doctorId, String medicalDate);

    List<MedicalSchedule> findByPatientProfileId(Long patientProfileId);
}