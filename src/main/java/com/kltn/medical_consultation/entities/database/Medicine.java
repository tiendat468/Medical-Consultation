package com.kltn.medical_consultation.entities.database;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "medicine")
public class Medicine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "active_ingredient")
    private String activeIngredient;
    @Column(name = "dosage_strength")
    private String dosageStrength;
    private Long quantity;
    private String unit;
    private String note;
    @Column(name = "price_per_unit")
    private Double pricePerUnit;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfileMedicine> profiles;
}
