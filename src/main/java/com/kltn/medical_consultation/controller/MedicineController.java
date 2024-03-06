package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.medicine.AddMedicineRequest;
import com.kltn.medical_consultation.models.medicine.DeleteMedicineRequest;
import com.kltn.medical_consultation.models.medicine.ListMedicineRequest;
import com.kltn.medical_consultation.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/medicine")
public class MedicineController extends BaseController {

    @Autowired
    MedicineService medicineService;

    @PostMapping("/list")
    public BaseResponse listMedicine(@RequestBody ListMedicineRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return medicineService.listMedicine(request);
    }

    @PostMapping("/search")
    public BaseResponse searchMedicine(@RequestBody AddMedicineRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return null;
    }

    @PostMapping("/add")
    public BaseResponse addMedicine(@RequestBody AddMedicineRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return medicineService.addMedicine(request);
    }

    @PostMapping("/delete")
    public BaseResponse deleteMedicine(@RequestBody DeleteMedicineRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return medicineService.deleteMedicine(request);
    }

    @PostMapping("/update")
    public BaseResponse updateMedicine(@RequestBody AddMedicineRequest request, HttpServletRequest httpServletRequest) throws ApiException {
        return null;
    }
}
