package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "patient_profile")
public class PatientProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symptom;

    private String diagnostic;

    @Column(name = "is_complete")
    private Boolean isComplete = false;

    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<MedicalSchedule> medicalSchedules;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "patient_id", insertable = false, updatable = false)
    private Long patientId;
}