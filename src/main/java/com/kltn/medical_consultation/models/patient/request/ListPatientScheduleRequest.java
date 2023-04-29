package com.kltn.medical_consultation.models.patient.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListPatientScheduleRequest extends SpecificationBaseRequest {
    private Long patientId;
    private Boolean isDone;
    private Boolean isPay;
    private String medicalDate;
    @JsonIgnore
    public Specification<MedicalSchedule> getSpecification() {
        Specification<MedicalSchedule> specification = (entity, cq, cb) -> cb.equal(entity.get("patientProfile").get("patientId"), patientId);

        if (StringUtils.isNotEmpty(medicalDate)) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("medicalDate"), medicalDate));
        }

        if (isDone != null) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("isDone"), isDone));
        }

        if (isPay != null) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("isPay"), isPay));
        }

        return specification;
    }
}
