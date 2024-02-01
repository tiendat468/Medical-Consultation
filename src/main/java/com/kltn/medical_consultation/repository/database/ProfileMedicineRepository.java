package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.ProfileMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileMedicineRepository extends JpaRepository<ProfileMedicine, Long> {
}