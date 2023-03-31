package com.kltn.medical_consultation.models.department.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentPercent {
    private String symbol;
    private Double percent = 0.0;
}
