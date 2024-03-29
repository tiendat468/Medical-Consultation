package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "academic_rank")
public class AcademicRank extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String symbol;

    private int priority;

    @JsonIgnore
    @OneToMany(mappedBy = "academicRank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<DoctorRank> doctorRanks;

}
