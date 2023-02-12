package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.auth.LoginRequest;
import com.kltn.medical_consultation.models.auth.LoginResponse;
import com.kltn.medical_consultation.models.auth.UserProfileResponse;
import com.kltn.medical_consultation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public BaseResponse logout(HttpServletRequest httpServletRequest) {
        return authService.logout(httpServletRequest);
    }

    @GetMapping("/profile")
    public BaseResponse<UserProfileResponse> getProfileUser(HttpServletRequest httpServletRequest) {
        return authService.getProfileUser(httpServletRequest);
    }
}
