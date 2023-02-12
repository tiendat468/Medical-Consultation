package com.kltn.medical_consultation.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

public class PageableUtils {

    public static PageRequest getPageRequest(Pageable pageable, Sort sort){

        if (pageable.isUnpaged()){
            return PageRequest.of(0, Integer.MAX_VALUE, sort);
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    public static PageRequest getPageRequest(HttpServletRequest httpServletRequest, Pageable pageable){
        Pageable tempPage = RestUtils.getPageable(httpServletRequest, pageable);

        if (tempPage.isUnpaged()){
            return PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort());
        }
        return PageRequest.of(tempPage.getPageNumber(), tempPage.getPageSize(), pageable.getSort());
    }

    public static PageRequest getPageRequest(Pageable pageable){
        if (pageable.isUnpaged()){
            return PageRequest.of(0, Integer.MAX_VALUE);
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }
}
