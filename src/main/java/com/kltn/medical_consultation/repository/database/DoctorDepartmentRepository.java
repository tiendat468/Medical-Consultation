package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.DoctorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDepartmentRepository extends JpaRepository<DoctorDepartment, Long> {
}