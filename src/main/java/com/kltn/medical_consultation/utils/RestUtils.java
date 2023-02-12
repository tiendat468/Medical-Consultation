package com.kltn.medical_consultation.utils;

import com.kltn.medical_consultation.models.ShareConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public class RestUtils {

    public static String getTokenFromHeader(HttpServletRequest request) {
        String token =  request.getHeader(ShareConstant.REST_HEADER.AUTHORIZATION);

        if (StringUtils.isEmpty(token)) return null;

        token = token.replaceFirst("(?i)" + ShareConstant.REST_HEADER.TOKEN_PREFIX, "");
        return  token.replace(" " , "");
    }


    public static String getIPRequest(HttpServletRequest servletRequest) {
        if (servletRequest == null)
            return null;
        String remote_iPAddress = null;
        remote_iPAddress = servletRequest.getHeader("X-FORWARDED-FOR");
        if (remote_iPAddress == null || "".equals(remote_iPAddress)) {
            remote_iPAddress = servletRequest.getRemoteAddr();
        }

        return remote_iPAddress;
    }

    public static String getDeviceId(HttpServletRequest servletRequest) {
        if (servletRequest == null)
            return null;
        return servletRequest.getHeader(ShareConstant.REST_HEADER.DEVICE_Id);
    }

    public static Pageable getPageable(HttpServletRequest request, Pageable pageable){
        String pageString = request.getParameter("page");
        String sizeString = request.getParameter("size");

        if (pageString == null || sizeString == null){
            return Pageable.unpaged();
        }

        if (pageString != null && StringUtils.isBlank(pageString)){
            return Pageable.unpaged();
        }

        if (sizeString != null && StringUtils.isBlank(sizeString)){
            return Pageable.unpaged();
        }

        if (!StringUtils.isNumeric(pageString + sizeString)){
            return Pageable.unpaged();
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }

}
