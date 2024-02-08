package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long>, JpaSpecificationExecutor<PatientProfile> {
    @Query("select p from PatientProfile p where p.patient.id = ?1 and p.isDelete = false")
    List<PatientProfile> findByPatientId(Long id);

    @Query("select p from PatientProfile p where p.id = ?1 and p.isDelete = false")
    Optional<PatientProfile> findByIdAndIsDeleteFalse(Long id);
//    Optional<PatientProfile> findById(Long id, Long userId);


}