package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.security.IAuthenticationFacade;
import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    protected ICheckBCryptPasswordEncoder passwordEncoder;

    @Autowired
    IAuthenticationFacade authenticationFacade;
}
