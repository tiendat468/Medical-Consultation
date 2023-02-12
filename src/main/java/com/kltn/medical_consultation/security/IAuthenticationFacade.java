package com.kltn.medical_consultation.security;

import com.kltn.medical_consultation.entities.redis.TokenDTO;

import javax.servlet.http.HttpServletRequest;

public interface IAuthenticationFacade {
    CustomUserDetail getAuthentication();
    TokenDTO getUserDetail();
    Long getUserId();
    String getIPAddress();
    HttpServletRequest getHttpServletRequest();
}
