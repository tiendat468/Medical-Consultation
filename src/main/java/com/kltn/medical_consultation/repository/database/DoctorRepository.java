package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByDepartmentId(Long id);

    Optional<Doctor> findByUserId(Long userId);
}