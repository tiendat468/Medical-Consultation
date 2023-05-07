package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.admin.request.ListPatientRequest;
import com.kltn.medical_consultation.models.admin.response.PatientResponse;
import com.kltn.medical_consultation.models.department.request.FetchDepartmentRequest;
import com.kltn.medical_consultation.models.department.request.ListDepartmentRequest;
import com.kltn.medical_consultation.models.department.response.DepartmentResponse;
import com.kltn.medical_consultation.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/department")
public class DepartmentController extends BaseController{
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/list")
    public BasePaginationResponse<DepartmentResponse> listPatients(@RequestBody ListDepartmentRequest request, Pageable pageable, HttpServletRequest httpServletRequest) {
//        authService.checkPermission(authenticationFacade.getUserId());
        return departmentService.listDepartments(request, pageable);
    }

    @PostMapping("/fetch")
    public BaseResponse fetchDepartment(@RequestBody FetchDepartmentRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return departmentService.fetchDepartment(request, httpServletRequest);
    }
}
