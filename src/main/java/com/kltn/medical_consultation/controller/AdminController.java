package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.admin.request.*;
import com.kltn.medical_consultation.models.admin.response.DoctorResponse;
import com.kltn.medical_consultation.models.admin.response.PatientResponse;
import com.kltn.medical_consultation.models.admin.response.StatsRevenueResponse;
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

    @PostMapping("/add-user")
    public BaseResponse addUser(@RequestBody AddUserRequest request) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.addUser(request);
    }

    @PostMapping("/activate")
    public BaseResponse activeUser(@RequestBody ActivateUserRequest request) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.activate(request);
    }

    @GetMapping("/doctors")
    public BasePaginationResponse<DoctorResponse> listDoctors(@RequestBody ListDoctorRequest listDoctorRequest, Pageable pageable, HttpServletRequest httpServletRequest) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.listDoctors(listDoctorRequest, pageable);
    }

    @GetMapping("/doctor/{doctorId}")
    public BaseResponse<DoctorResponse> getDoctorById(@PathVariable Long doctorId, HttpServletRequest httpServletRequest) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.getDoctorById(doctorId);
    }

    @GetMapping("/patients")
    public BasePaginationResponse<PatientResponse> listPatients(@RequestBody ListPatientRequest listPatientRequest, Pageable pageable, HttpServletRequest httpServletRequest) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.listPatients(listPatientRequest, pageable);
    }

    @GetMapping("/patient/{patientId}")
    public BaseResponse<PatientResponse> getPatientById(@PathVariable Long patientId, HttpServletRequest httpServletRequest) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.getPatientById(patientId);
    }

    @GetMapping("/stats-revenue")
    public BaseResponse<StatsRevenueResponse> statsRevenue(HttpServletRequest httpServletRequest) {
        authService.checkPermission(authenticationFacade.getUserId());
        return adminService.statsRevenue();
    }

}
