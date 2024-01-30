package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "patient")
public class Patient extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "sex")
    private String sex;
    @Column(name = "address")
    private String address;
    @Column(name = "job")
    private String job;
    @Column(name = "identity_number")
    private String identityNumber;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "weight")
    private Long weight;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PatientProfile> patientProfiles;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
