package com.kltn.medical_consultation.entities.database;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "register_activity")
@Data
public class RegisterActivity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    public Long userId;
    @Column(name = "email")
    public String email;
    @Column(name = "code")
    public String code;
    @Column(name = "type")
    public String type;

}

