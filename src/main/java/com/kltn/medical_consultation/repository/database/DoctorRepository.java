package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByDepartment_Id(Long id);
}