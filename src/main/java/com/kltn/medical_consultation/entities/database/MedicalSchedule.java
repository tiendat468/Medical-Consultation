package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "medical_schedule")
public class MedicalSchedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime medical_date;

    private Double total_price;

    private boolean status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id")
    private PatientProfile patient;

    @Column(name = "patient_id", insertable = false, updatable = false)
    private Long patientId;

    @JsonIgnore
    @OneToMany(mappedBy = "medicalSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<MedicalScheduleDetail> medicalScheduleDetails;
}
