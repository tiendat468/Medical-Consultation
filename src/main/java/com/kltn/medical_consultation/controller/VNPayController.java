package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.vnpay.PaymentDTO;
import com.kltn.medical_consultation.services.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/vnpay")
public class VNPayController extends BaseController{

    @Autowired
    VNPayService vnPayService;

    @PostMapping("/create-payment")
    public BaseResponse createPayment(@RequestBody PaymentDTO paymentDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        return vnPayService.createPayment(paymentDTO, request);
    }
}
