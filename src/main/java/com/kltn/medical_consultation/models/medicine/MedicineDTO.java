package com.kltn.medical_consultation.models.medicine;

import com.kltn.medical_consultation.entities.database.Medicine;
import com.kltn.medical_consultation.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDTO {

    private Long id;
    private String name;
    private String activeIngredient;
    private String dosageStrength;
    private Long quantity;
    private String unit;
    private Double pricePerUnit;
    private String note;
    private String createdAt;

    public MedicineDTO(Medicine medicine) {
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.activeIngredient = medicine.getActiveIngredient();
        this.dosageStrength = medicine.getDosageStrength();
        this.quantity = medicine.getQuantity();
        this.unit = medicine.getUnit();
        this.note = medicine.getNote();
        this.pricePerUnit = medicine.getPricePerUnit();
        this.createdAt = TimeUtils.dateToStringSimpleDateFormat2(Date.from(medicine.getCreatedAt().toInstant()));
    }
}
