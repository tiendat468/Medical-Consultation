package com.kltn.medical_consultation.models.medicine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListMedicineRequest {
    private String name;
    private Boolean isPage;
    private String order;
    private String orderBy;
    private Integer pageIndex;
    private Integer pageSize;

    public void buildJpaQuery() {
        this.buildJpaPaging();
        if (StringUtils.isEmpty(this.name)) {
            this.name = "%%";
        } else {
            this.name = "%" + this.name + "%";
        }
    }

    public void buildJpaPaging() {
        if (ObjectUtils.isEmpty(this.pageIndex)) {
            this.pageIndex = 0;
        }
        if (ObjectUtils.isEmpty(this.pageSize)) {
            this.pageSize = 10;
        }
        if (StringUtils.isEmpty(this.order)) {
            this.order = "ASC";
        }
        if (StringUtils.isEmpty(this.orderBy)) {
            this.orderBy = "name";
        }
    }
}
