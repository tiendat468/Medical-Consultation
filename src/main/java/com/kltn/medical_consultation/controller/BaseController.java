package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @Autowired
    protected IAuthenticationFacade authenticationFacade;

}


