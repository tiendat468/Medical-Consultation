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
        return vnPayService.createPayment(paymentDTO, authenticationFacade.getUserId(), request);
    }

    @GetMapping("/get-payment")
    public BaseResponse getPayment(@RequestParam String vnpTxnRef, HttpServletRequest request) {
        return vnPayService.getPayment(vnpTxnRef, request);
    }

    @GetMapping("/update-payment")
    public BaseResponse updatePayment(@RequestParam("vnp_BankCode") String vnpBankCode,
                                      @RequestParam(name = "vnp_BankTranNo", required = false) String vnpBankTranNo,
                                      @RequestParam("vnp_CardType") String vnpCardType,
                                      @RequestParam("vnp_PayDate") String vnpPayDate,
                                      @RequestParam("vnp_ResponseCode") String vnpResponseCode,
                                      @RequestParam("vnp_SecureHash") String vnpSecureHash,
                                      @RequestParam("vnp_TmnCode") String vnpTmnCode,
                                      @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
                                      @RequestParam("vnp_TransactionStatus")String vnpTransactionStatus,
                                      @RequestParam("vnp_TxnRef") String vnpTxnRef,
                                      @RequestParam("vnp_Amount") String vnpAmount,
                                      @RequestParam("vnp_OrderInfo") String vnpOrderInfo) {
        PaymentDTO paymentDTO = new PaymentDTO(vnpBankCode, vnpBankTranNo, vnpCardType, vnpPayDate, vnpResponseCode, vnpSecureHash, vnpTmnCode, vnpTransactionNo, vnpTransactionStatus, vnpTxnRef, vnpAmount, vnpOrderInfo);
        return new BaseResponse(paymentDTO);
//        return vnPayService.updatePayment(paymentDTO);
    }

}
