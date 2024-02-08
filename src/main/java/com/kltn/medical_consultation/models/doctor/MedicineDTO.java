package com.kltn.medical_consultation.models.doctor;

import com.kltn.medical_consultation.entities.database.Medicine;
import com.kltn.medical_consultation.entities.database.ProfileMedicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDTO {
    private Long id;
    private String name;
    private Long quantity;
    private String unit;
    private String instruction;

    public MedicineDTO(Medicine medicine) {
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.quantity = medicine.getQuantity();
        this.unit = medicine.getUnit();
    }

    public MedicineDTO(ProfileMedicine profileMedicine) {
        if (profileMedicine.getMedicine() != null) {
            this.id = profileMedicine.getMedicine().getId();
        }
        this.name = profileMedicine.getName();
        this.quantity = profileMedicine.getQuantity();
        this.unit = profileMedicine.getUnit();
        this.instruction = profileMedicine.getInstruction();
    }
}
