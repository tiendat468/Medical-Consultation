package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "doctor_rank")
public class DoctorRank extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;

    @Column(name = "doctor_id", insertable = false, updatable = false)
    private Long doctorId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "academic_id")
    private AcademicRank academicRank;

    @Column(name = "academic_id", insertable = false, updatable = false)
    private Long academicId;

}
