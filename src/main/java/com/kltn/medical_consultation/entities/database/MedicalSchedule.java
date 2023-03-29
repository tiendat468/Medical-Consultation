package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "medical_schedule")
public class MedicalSchedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime medicalDate;

    private Double totalPrice;

    private boolean status;

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
