package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.response.DetailDoctorScheduleResponse;
import com.kltn.medical_consultation.models.doctor.response.DoctorScheduleResponse;
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

    @PostMapping("/schedule/list")
    public BasePaginationResponse<DoctorScheduleResponse> listSchedule(@RequestBody ListDoctorScheduleRequest listDoctorScheduleRequest, Pageable pageable, HttpServletRequest httpServletRequest) {
        return doctorService.listSchedule(listDoctorScheduleRequest, pageable, httpServletRequest);
    }

    @PostMapping("/schedule/detail")
    public BaseResponse<DetailDoctorScheduleResponse> detailSchedule(@RequestBody DetailDoctorScheduleRequest detailDoctorScheduleRequest, HttpServletRequest httpServletRequest) {
        return doctorService.detailSchedule(detailDoctorScheduleRequest, httpServletRequest);
    }

    @PostMapping("/schedule/done/{scheduleId}")
    public BaseResponse checkDone(@PathVariable Long scheduleId, HttpServletRequest httpServletRequest) {
        return doctorService.checkDone(scheduleId, authenticationFacade.getUserId(), httpServletRequest);
    }
}
