package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.doctor.request.DetailPatientProfileRequest;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.SaveProfileRequest;
import com.kltn.medical_consultation.models.doctor.request.MedicalPatientRequest;
import com.kltn.medical_consultation.models.doctor.response.DoctorProfileResponse;
import com.kltn.medical_consultation.models.schedule.response.SchedulesResponse;
import com.kltn.medical_consultation.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/doctor")
public class DoctorController extends BaseController{
    @Autowired
    DoctorService doctorService;

    @GetMapping("/profile")
    public BaseResponse<DoctorProfileResponse> getProfile(HttpServletRequest httpServletRequest) throws ApiException {
        return doctorService.getProfile(authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/profile/update")
    public BaseResponse updateProfile(@RequestBody SaveProfileRequest request, HttpServletRequest httpServletRequest) throws ApiException{
        return doctorService.updateProfile(request, authenticationFacade.getUserId(), httpServletRequest);
    }

    @PostMapping("/schedules")
    public BasePaginationResponse<SchedulesResponse> listSchedule(@RequestBody ListDoctorScheduleRequest listDoctorScheduleRequest, Pageable pageable, HttpServletRequest httpServletRequest) {
        return doctorService.listSchedule(listDoctorScheduleRequest, pageable, httpServletRequest);
    }

    @PostMapping("/schedule/detail")
    public BaseResponse<SchedulesResponse> detailSchedule(@RequestBody DetailPatientProfileRequest detailPatientProfileRequest, HttpServletRequest httpServletRequest) {
        return doctorService.detailSchedule(detailPatientProfileRequest, httpServletRequest);
    }

    @PostMapping("/schedule/update")
    public BaseResponse medicalPatient(@RequestBody MedicalPatientRequest medicalPatientRequest, HttpServletRequest httpServletRequest) {
        return doctorService.medicalPatient(medicalPatientRequest, httpServletRequest);
    }
}
