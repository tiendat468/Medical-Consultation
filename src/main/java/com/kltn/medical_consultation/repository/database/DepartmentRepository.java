package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findBySymbol(String symbol);
}