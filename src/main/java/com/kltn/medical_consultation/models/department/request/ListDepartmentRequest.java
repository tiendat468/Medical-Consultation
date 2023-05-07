package com.kltn.medical_consultation.models.department.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.Department;
import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListDepartmentRequest extends SpecificationBaseRequest {
    private String name;

    @JsonIgnore
    public Specification<Department> getSpecification() {
        Specification<Department> specification = Specification.where(null);

        if (StringUtils.isNotBlank(name)) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.like(entity.get("name"), "%" + name + "%"));
        }
        return specification;
    }
}
