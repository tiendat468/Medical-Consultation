package com.kltn.medical_consultation.models.admin.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class StatsRevenueRequest extends SpecificationBaseRequest {
    private Integer year;

    @JsonIgnore
    public Specification<MedicalSchedule> getSpecification() {
        Specification<MedicalSchedule> specification = Specification.where(null);
        specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) -> cb.equal(entity.get("isDelete"), false));
        specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) -> cb.equal(entity.get("isPay"), true));
        specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) -> cb.like(entity.get("medicalDate"), "%" + year + "%"));
        return specification;
    }
}
