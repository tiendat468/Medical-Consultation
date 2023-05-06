package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndType(String email, int type);

    List<User> findByCreatedAt(OffsetDateTime createdAt);
}
