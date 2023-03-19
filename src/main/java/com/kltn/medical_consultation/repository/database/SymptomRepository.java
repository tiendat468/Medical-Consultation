package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
}