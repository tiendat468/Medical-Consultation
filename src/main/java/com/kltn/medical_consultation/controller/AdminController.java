package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.admin.request.ListDoctorRequest;
import com.kltn.medical_consultation.models.admin.response.DoctorResponse;
import com.kltn.medical_consultation.models.auth.request.LoginRequest;
import com.kltn.medical_consultation.models.auth.response.LoginResponse;
import com.kltn.medical_consultation.services.AdminService;
import com.kltn.medical_consultation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    AuthService authService;
    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request, true);
    }

    @GetMapping("/doctors")
    public BasePaginationResponse<DoctorResponse> listDoctors(@RequestBody ListDoctorRequest listDoctorRequest, Pageable pageable, HttpServletRequest httpServletRequest) {
        return adminService.listDoctors(listDoctorRequest, pageable);
    }
}
