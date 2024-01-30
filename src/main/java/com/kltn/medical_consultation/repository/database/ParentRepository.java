package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByPhoneNumber(String phoneNumber);

}