package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.DoctorRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRankRepository extends JpaRepository<DoctorRank, Long> {
}