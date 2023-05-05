package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.auth.request.LoginRequest;
import com.kltn.medical_consultation.models.auth.response.LoginResponse;
import com.kltn.medical_consultation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request, true);
    }
}
