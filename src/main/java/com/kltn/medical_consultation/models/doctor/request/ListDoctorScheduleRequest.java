package com.kltn.medical_consultation.models.doctor.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.entities.database.MedicalSchedule;
import com.kltn.medical_consultation.entities.database.PatientProfile;
import com.kltn.medical_consultation.models.SpecificationBaseRequest;
import com.kltn.medical_consultation.utils.SpecificationUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListDoctorScheduleRequest extends SpecificationBaseRequest {
    private Long doctorId;
    private String medicalDate;
    private Boolean isDone;
    private Boolean isPay;
    @JsonIgnore
    public Specification<MedicalSchedule> getSpecification() {
        Specification<MedicalSchedule> specification = getBaseSpecification();

        specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) -> cb.equal(entity.get("doctorId"), doctorId));
        if (StringUtils.isNotEmpty(medicalDate)) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("medicalDate"), medicalDate));
        }

        if (isDone != null) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("isDone"), isDone));
        }

//        if (isPay != null) {
            specification = SpecificationUtil.addSpecification(specification, (entity, cq, cb) ->
                    cb.equal(entity.get("isPay"), true));
//        }

        return specification;
    }
}
