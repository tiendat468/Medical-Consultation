package com.kltn.medical_consultation.models.vnpay.response;

import com.kltn.medical_consultation.entities.database.VnpayPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VNPayPaymentResponse {
    private Long id;
    private String vnp_ResponseId;
    private String vnp_Command;
    private String vnp_ResponseCode;
    private String vnp_Message;
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private BigDecimal vnp_Amount = BigDecimal.valueOf(0);
    private String vnp_OrderInfo;
    private String vnp_BankCode;
    private String vnp_PayDate;
    private String vnp_TransactionNo;
    private String vnp_TransactionType;
    private String vnp_TransactionStatus;
    private String vnp_CardNumber;
    private String vnp_Trace;
    private String vnp_CardHolder;
    private String vnp_FeeAmount;
    private String vnp_SecureHash;
    private Boolean is_done = false;
    private Long paymentId;

    public VNPayPaymentResponse(VnpayPayment vnpayPayment) {
        this.id = vnpayPayment.getId();
        this.vnp_ResponseId = vnpayPayment.getVnp_ResponseId();
        this.vnp_Command = vnpayPayment.getVnp_Command();
        this.vnp_ResponseCode = vnpayPayment.getVnp_ResponseCode();
        this.vnp_Message = vnpayPayment.getVnp_Message();
        this.vnp_TmnCode = vnpayPayment.getVnp_TmnCode();
        this.vnp_TxnRef = vnpayPayment.getVnp_TxnRef();
        this.vnp_Amount = vnpayPayment.getVnp_Amount();
        this.vnp_OrderInfo = vnpayPayment.getVnp_OrderInfo();
        this.vnp_BankCode = vnpayPayment.getVnp_BankCode();
        this.vnp_PayDate = vnpayPayment.getVnp_PayDate();
        this.vnp_TransactionNo = vnpayPayment.getVnp_TransactionNo();
        this.vnp_TransactionType = vnpayPayment.getVnp_TransactionType();
        this.vnp_TransactionStatus = vnpayPayment.getVnp_TransactionStatus();
        this.vnp_CardNumber = vnpayPayment.getVnp_CardNumber();
        this.vnp_Trace = vnpayPayment.getVnp_Trace();
        this.vnp_CardHolder = vnpayPayment.getVnp_CardHolder();
        this.vnp_FeeAmount = vnpayPayment.getVnp_FeeAmount();
        this.vnp_SecureHash = vnpayPayment.getVnp_SecureHash();
        this.is_done = vnpayPayment.getIs_done();
        this.paymentId = vnpayPayment.getPaymentId();
    }
}
