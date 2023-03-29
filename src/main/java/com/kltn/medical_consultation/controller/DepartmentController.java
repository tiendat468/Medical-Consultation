package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.schedule.request.FetchDepartmentRequest;
import com.kltn.medical_consultation.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/department")
public class DepartmentController extends BaseController{
    @Autowired
    DepartmentService departmentService;
    @PostMapping("/fetch")
    public BaseResponse fetchDepartment(@RequestBody FetchDepartmentRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return departmentService.fetchDepartment(request, httpServletRequest);
    }
}
