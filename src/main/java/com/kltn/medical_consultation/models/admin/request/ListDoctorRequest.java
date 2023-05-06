package com.kltn.medical_consultation.models.admin.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.Doctor;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListDoctorRequest extends SpecificationBaseRequest {
    private Long departmentId;

    @JsonIgnore
    public Specification<Doctor> getSpecification() {
        Specification<Doctor> specification = Specification.where(null);

        if (departmentId != null) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("departmentId"), departmentId));
        }
        return specification;
    }
}
