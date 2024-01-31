package com.kltn.medical_consultation.entities.database;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "profile_medicine")
public class ProfileMedicine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    private String unit;
    private String instruction;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "patient_profile_id")
    private PatientProfile patientProfile;
}
