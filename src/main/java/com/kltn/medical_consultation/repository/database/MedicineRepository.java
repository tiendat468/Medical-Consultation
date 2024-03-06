package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByIdAndIsDeleteFalse(Long id);

    @Query("SELECT m FROM Medicine m " +
            "WHERE m.isDelete = FALSE " +
            "AND (m.name LIKE :searchValue OR m.activeIngredient LIKE :searchValue)")
    Page<Medicine> listMedicinePage(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("SELECT m FROM Medicine m " +
            "WHERE m.isDelete = FALSE " +
            "AND (m.name LIKE :searchValue OR m.activeIngredient LIKE :searchValue)")
    List<Medicine> listMedicine(@Param("searchValue") String searchValue);
}