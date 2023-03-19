package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser_Id(Long id);

}