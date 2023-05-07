package com.kltn.medical_consultation.models.admin.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListDoctorRequest extends SpecificationBaseRequest {
    private Long departmentId;
    private String email;

    @JsonIgnore
    public Specification<Doctor> getSpecification() {
        Specification<Doctor> specification = Specification.where(null);

        if (departmentId != null) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("departmentId"), departmentId));
        }

        if (StringUtils.isNotBlank(email)) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.like(entity.get("user").get("email"), "%" + email + "%"));
        }
        return specification;
    }
}
