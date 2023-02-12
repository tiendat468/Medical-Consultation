package com.kltn.medical_consultation.security;

import com.kltn.medical_consultation.entities.redis.TokenDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Override
    public CustomUserDetail getAuthentication() {
        return (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public TokenDTO getUserDetail() {
        return (TokenDTO) this.getAuthentication().getPrincipal();
    }

    @Override
    public Long getUserId() {
        return this.getUserDetail().getUserId();
    }

    @Override
    public String getIPAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getRemoteAddr();
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request;
    }

}
