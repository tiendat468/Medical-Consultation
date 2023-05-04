package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.patient.request.*;
import com.kltn.medical_consultation.models.schedule.response.SchedulesResponse;
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

//    @GetMapping("/list")
//    public BasePaginationResponse<PatientProfileResponse> listProfile(
//            Pageable pageable,
//            HttpServletRequest httpServletRequest) throws ApiException {
//        return patientService.listProfile(authenticationFacade.getUserId(), pageable, httpServletRequest);
//    }

    @PostMapping("/save")
    public BaseResponse saveProfile(@RequestBody SavePatientRequest request, HttpServletRequest httpServletRequest) throws ApiException{
        return patientService.savePatient(request, authenticationFacade.getUserId(), httpServletRequest);
    }

    @GetMapping("/detail")
    public BaseResponse detailPatient(HttpServletRequest httpServletRequest) throws ApiException{
        return patientService.detailPatient(authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/profile/save")
    public BaseResponse createPatientProfile(@RequestBody CreatePatientProfileRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return patientService.createPatientProfile(request, authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/profile/detail")
    public BaseResponse detailPatientProfile(@RequestBody DetailProfileRequest request, HttpServletRequest httpServletRequest) throws ApiException{
        return patientService.detailPatientProfile(request, httpServletRequest);
    }

    @PostMapping("/profile/update")
    public BaseResponse updatePatientProfile(@RequestBody UpdateProfileRequest request, HttpServletRequest httpServletRequest) throws ApiException{
        return patientService.updatePatientProfile(request, httpServletRequest);
    }

    @PostMapping("/schedules")
    public BasePaginationResponse getSchedules(@RequestBody ListPatientScheduleRequest request, Pageable pageable, HttpServletRequest httpServletRequest) {
        return patientService.getSchedules(request, pageable, httpServletRequest);
    }

    @PostMapping("/schedule/detail")
    public BaseResponse<SchedulesResponse> detailSchedule(@RequestBody DetailDoctorScheduleRequest detailDoctorScheduleRequest, HttpServletRequest httpServletRequest) {
        return patientService.detailSchedule(detailDoctorScheduleRequest, httpServletRequest);
    }

//    @PostMapping("/edit")
//    public BaseResponse editPatientProfile(@RequestBody EditPatientProfileRequest request, HttpServletRequest httpServletRequest)  throws ApiException{
//        return patientService.editPatientProfile(request, authenticationFacade.getUserId(), httpServletRequest);
//    }
//
//    @PostMapping("/delete")
//    public BaseResponse deletePatientProfile(@RequestBody DetailProfileRequest request, HttpServletRequest httpServletRequest)  throws ApiException{
//        return patientService.deleteProfile(request, authenticationFacade.getUserId(), httpServletRequest);
//    }
}
