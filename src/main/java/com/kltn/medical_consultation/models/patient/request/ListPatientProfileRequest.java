package com.kltn.medical_consultation.models.patient.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

public class ListPatientProfileRequest extends SpecificationBaseRequest {
    @JsonIgnore
    public Specification<PatientProfile> getSpecification(Long userId) {
        Specification<PatientProfile> specification = (entity, cq, cb) -> cb.equal(entity.get("isDelete"), false);

        specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                cb.equal(entity.get("userId"), userId));
        return specification;
    }
}
