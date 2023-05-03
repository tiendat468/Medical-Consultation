package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.doctor.request.DetailDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.ListDoctorScheduleRequest;
import com.kltn.medical_consultation.models.doctor.request.UpdateScheduleRequest;
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

    @PostMapping("/schedule/list")
    public BasePaginationResponse<SchedulesResponse> listSchedule(@RequestBody ListDoctorScheduleRequest listDoctorScheduleRequest, Pageable pageable, HttpServletRequest httpServletRequest) {
        return doctorService.listSchedule(listDoctorScheduleRequest, pageable, httpServletRequest);
    }

    @PostMapping("/schedule/detail")
    public BaseResponse<SchedulesResponse> detailSchedule(@RequestBody DetailDoctorScheduleRequest detailDoctorScheduleRequest, HttpServletRequest httpServletRequest) {
        return doctorService.detailSchedule(detailDoctorScheduleRequest, httpServletRequest);
    }

    @PostMapping("/schedule/update")
    public BaseResponse checkDone(@RequestBody UpdateScheduleRequest updateScheduleRequest, HttpServletRequest httpServletRequest) {
        return doctorService.checkDone(updateScheduleRequest, httpServletRequest);
    }
}
