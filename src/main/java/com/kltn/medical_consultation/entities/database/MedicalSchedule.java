package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "medical_schedule")
public class MedicalSchedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_date")
    private String medicalDate;

    private String hours;

    private Double price = 0.0;

    private Boolean status = false;
    @Column(name = "is_done")
    private Boolean isDone = false;
    @Column(name = "is_pay")
    private Boolean isPay = false;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_profile_id")
    private PatientProfile patientProfile;

    @Column(name = "patient_profile_id", insertable = false, updatable = false)
    private Long patientProfileId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "doctor_id", insertable = false, updatable = false)
    private Long doctorId;
}
