package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.entities.database.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByUser_Id(Long id);

    Optional<Patient> findByUserId(Long userId);

}