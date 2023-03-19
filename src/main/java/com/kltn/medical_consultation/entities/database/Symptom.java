package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "symptom")
public class Symptom extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int percent;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;
}
