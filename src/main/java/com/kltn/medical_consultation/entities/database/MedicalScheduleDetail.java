package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "medical_schedule_detail")
public class MedicalScheduleDetail extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private String startTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "medical_schedule_id")
    private MedicalSchedule medicalSchedule;

    @Column(name = "medical_schedule_id", insertable = false, updatable = false)
    private Long medicalScheduleId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;

    @Column(name = "doctor_id", insertable = false, updatable = false)
    private Long doctorId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;
}
