package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.patient.CreatePatientProfileRequest;
import com.kltn.medical_consultation.models.patient.DetailProfileRequest;
import com.kltn.medical_consultation.models.patient.EditPatientProfileRequest;
import com.kltn.medical_consultation.models.patient.PatientProfileResponse;
import com.kltn.medical_consultation.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/patient")
public class PatientController extends BaseController {

    @Autowired
    PatientService patientService;

    @GetMapping("/list")
    public BasePaginationResponse<PatientProfileResponse> listProfile(
            Pageable pageable,
            HttpServletRequest httpServletRequest) throws ApiException {
        return patientService.listProfile(authenticationFacade.getUserId(), pageable, httpServletRequest);
    }

    @PostMapping("/detail")
    public BaseResponse detailProfile(@RequestBody DetailProfileRequest request, HttpServletRequest httpServletRequest) throws ApiException{
        return patientService.detailProfile(request, authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/create")
    public BaseResponse createPatientProfile(@RequestBody CreatePatientProfileRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return patientService.createPatientProfile(request, authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/edit")
    public BaseResponse editPatientProfile(@RequestBody EditPatientProfileRequest request, HttpServletRequest httpServletRequest)  throws ApiException{
        return patientService.editPatientProfile(request, authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/delete")
    public BaseResponse deletePatientProfile(@RequestBody DetailProfileRequest request, HttpServletRequest httpServletRequest)  throws ApiException{
        return patientService.deleteProfile(request, authenticationFacade.getUserId(), httpServletRequest);
    }
}
