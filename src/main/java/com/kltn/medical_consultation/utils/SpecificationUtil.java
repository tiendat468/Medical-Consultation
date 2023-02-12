package com.kltn.medical_consultation.utils;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

    public static Specification addSpecification(Specification initSpecification, Specification specification){
        if (initSpecification != null){
            return initSpecification.and(specification);
        }else {
            return specification;
        }
    }
    public static Specification orSpecification(Specification initSpecification, Specification specification){
        if (initSpecification != null){
            return initSpecification.or(specification);
        }else {
            return specification;
        }
    }
}
