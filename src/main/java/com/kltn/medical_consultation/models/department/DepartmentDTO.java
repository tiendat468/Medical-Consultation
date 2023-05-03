package com.kltn.medical_consultation.models.department;

import com.kltn.medical_consultation.entities.database.Department;
import lombok.Data;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private String symbol;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.symbol = department.getSymbol();
    }
}
