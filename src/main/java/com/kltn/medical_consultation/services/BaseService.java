package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.redis.TokenDTO;
import com.kltn.medical_consultation.repository.redis.RedisTokenRepository;
import com.kltn.medical_consultation.security.IAuthenticationFacade;
import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import com.kltn.medical_consultation.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class BaseService {
    @Autowired
    protected ICheckBCryptPasswordEncoder passwordEncoder;

    @Autowired
    IAuthenticationFacade authenticationFacade;

    @Autowired
    RedisTokenRepository tokenRepository;

    public TokenDTO checkExpiredToken(HttpServletRequest httpServletRequest) {
        String token = RestUtils.getTokenFromHeader(httpServletRequest);
        Optional<TokenDTO> tokenOptional = tokenRepository.findById(token);

        if (tokenOptional.isPresent()) {
            return tokenOptional.get();
        }
        return null;
    }
}
