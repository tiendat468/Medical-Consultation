package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByDepartment_Id(Long id);
}