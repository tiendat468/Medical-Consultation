package com.kltn.medical_consultation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Data
public class SpecificationBaseRequest {
    private static final Logger LOGGER = LogManager.getLogger(SpecificationBaseRequest.class);
    private enum OrderBy {
        ASC("ASC"),
        DESC("DESC"),
        ;
        private String value;
        OrderBy(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    private String orderBy;

    @JsonIgnore
    public Specification getBaseSpecification(){
        Specification specification = (entity, cq, cb) -> cb.equal(entity.get("isDelete"), false);

        return specification;
    }

    @JsonIgnore
    public Sort getSort(){
        if(StringUtils.isNotEmpty(getOrderBy())){
            LOGGER.info("getOrderBy {}, OrderBy.DESC.getValue {}", getOrderBy(), OrderBy.DESC.getValue());
            if (getOrderBy().equalsIgnoreCase(OrderBy.DESC.getValue())){
                return Sort.by(Sort.Direction.DESC, "id");
            }
        }
        return Sort.by(Sort.Direction.ASC, "id");
    }

    @JsonIgnore
    public Sort getSortSchedule(){
        if(StringUtils.isNotEmpty(getOrderBy())){
            if (getOrderBy().equalsIgnoreCase(OrderBy.ASC.getValue())){
                return Sort.by(Sort.Direction.ASC, "medicalDate");
            }
        }
        return Sort.by(Sort.Direction.DESC, "medicalDate");
    }

    @JsonIgnore
    public Sort getSortUser(){
        if(StringUtils.isNotEmpty(getOrderBy())){
            LOGGER.info("getOrderBy {}, OrderBy.DESC.getValue {}", getOrderBy(), OrderBy.DESC.getValue());
            if (getOrderBy().equalsIgnoreCase(OrderBy.DESC.getValue())){
                Sort.Order orderActive = new Sort.Order(Sort.Direction.DESC, "isActive");
                Sort.Order orderId = new Sort.Order(Sort.Direction.ASC, "id");
                return Sort.by(orderActive, orderId);
            }
        }
        Sort.Order orderActive = new Sort.Order(Sort.Direction.DESC, "isActive");
        Sort.Order orderId = new Sort.Order(Sort.Direction.ASC, "id");
        return Sort.by(orderActive, orderId);
    }
}
