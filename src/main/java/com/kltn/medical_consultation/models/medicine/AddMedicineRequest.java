package com.kltn.medical_consultation.models.medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMedicineRequest {

    private String name;
    private String activeIngredient;
    private String unit;
    private String dosageStrength;
    private Long quantity;
    private Double pricePerUnit;
    private String note;
}
