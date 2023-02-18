package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long>, JpaSpecificationExecutor<PatientProfile> {
    Optional<PatientProfile> findByIdAndUserId(Long id, Long userId);
}