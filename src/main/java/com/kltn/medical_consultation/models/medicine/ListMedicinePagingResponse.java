package com.kltn.medical_consultation.models.medicine;

import lombok.Data;

import java.util.List;

@Data
public class ListMedicinePagingResponse {
    private List<MedicineDTO> medicines;
    private Long totalItems;
    private String order;
    private String orderBy;
    private Integer pageIndex;
    private Integer pageSize;
}
