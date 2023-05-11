package com.kltn.medical_consultation.repository.database;

import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalScheduleRepository extends JpaRepository<MedicalSchedule, Long>, JpaSpecificationExecutor<MedicalSchedule> {
    Optional<MedicalSchedule> findByPayCode(String payCode);
    List<MedicalSchedule> findByDoctorIdAndMedicalDateAndIsDeleteFalse(Long doctorId, String medicalDate);

    List<MedicalSchedule> findByPatientProfileId(Long patientProfileId);

    @Query("select count(m) from MedicalSchedule m " +
            "where m.medicalDate like ?1 and m.doctor.departmentId = ?2 and m.isDelete = false")
    long countByMedicalDateLikeAndDoctor_DepartmentIdAndIsDeleteFalse(String medicalDate, Long departmentId);

    @Query(value = "select count(m) from MedicalSchedule m " +
            "where m.medicalDate like ?1 and m.doctor.departmentId = ?2 and m.isDelete = false and m.isPay = true")
    long countByMedicalDateLikeAndDoctor_DepartmentIdAndIsDeleteFalseAndIsPayTrue(String medicalDate, Long departmentId);

    @Query("select count(m) from MedicalSchedule m " +
            "where m.medicalDate like ?1 and m.doctor.departmentId = ?2 and m.isDelete = false and m.isPay = false")
    long countByMedicalDateLikeAndDoctor_DepartmentIdAndIsDeleteFalseAndIsPayFalse(String medicalDate, Long departmentId);

    @Query("select count(m) from MedicalSchedule m " +
            "where m.medicalDate like ?1 and m.doctor.departmentId = ?2 and m.isDelete = false and m.isDone = true")
    long countByMedicalDateLikeAndDoctor_DepartmentIdAndIsDeleteFalseAndIsDoneTrue(String medicalDate, Long departmentId);

    @Query("select count(m) from MedicalSchedule m " +
            "where m.medicalDate like ?1 and m.doctor.departmentId = ?2 and m.isDelete = false and m.isDone = false")
    long countByMedicalDateLikeAndDoctor_DepartmentIdAndIsDeleteFalseAndIsDoneFalse(String medicalDate, Long departmentId);
}