package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.RegisterActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterActivityRepository extends JpaRepository<RegisterActivity, Long> {

    Optional<RegisterActivity> findAllByCode(String code);
}