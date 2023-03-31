package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.schedule.request.SaveScheduleRequest;
import com.kltn.medical_consultation.models.schedule.response.DetailScheduleResponse;
import com.kltn.medical_consultation.services.MedicalScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/schedule")
public class MedicalScheduleController extends BaseController{

    @Autowired
    MedicalScheduleService scheduleService;

    @PostMapping("/save")
    public BaseResponse<DetailScheduleResponse> save(@RequestBody SaveScheduleRequest request, HttpServletRequest httpServletRequest) {
        return scheduleService.save(request, httpServletRequest);
    }
}
