package com.kltn.medical_consultation.models.vnpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kltn.medical_consultation.entities.database.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {
    private Long id;
    private String vnp_BankCode;
    private BigDecimal vnp_Amount;
    private String vnp_BankTranNo;
    private String vnp_CardType;
    private String vnp_OrderInfo;
    private String vnp_PayDate;
    private String vnp_ResponseCode;
    private String vnp_TmnCode;
    private String vnp_TransactionNo;
    private String vnp_TransactionStatus;
    private String vnp_TxnRef;
    private String vnp_SecureHash;
    private Long user_id;
    private Boolean hook_status;

    public PaymentDTO(String vnp_BankCode, String vnpBankTranNo, String vnp_CardType, String vnp_PayDate, String vnp_ResponseCode, String vnp_SecureHash, String vnp_TmnCode, String vnp_TransactionNo, String vnp_TransactionStatus, String vnp_TxnRef, String vnp_Amount, String vnp_OrderInfo) {
        this.vnp_BankCode = vnp_BankCode;
        this.vnp_Amount = new BigDecimal(vnp_Amount);
        this.vnp_BankTranNo = vnp_BankTranNo;
        this.vnp_CardType = vnp_CardType;
        this.vnp_OrderInfo = vnp_OrderInfo;
        this.vnp_PayDate = vnp_PayDate;
        this.vnp_ResponseCode = vnp_ResponseCode;
        this.vnp_TmnCode = vnp_TmnCode;
        this.vnp_TransactionNo = vnp_TransactionNo;
        this.vnp_TransactionStatus = vnp_TransactionStatus;
        this.vnp_TxnRef = vnp_TxnRef;
        this.vnp_SecureHash = vnp_SecureHash;
    }

    public PaymentDTO paymentDTO(String vnp_BankCode, String vnpBankTranNo, String vnp_CardType, String vnp_PayDate, String vnp_ResponseCode, String vnp_SecureHash, String vnp_TmnCode, String vnp_TransactionNo, String vnp_TransactionStatus, String vnp_TxnRef, String vnp_Amount, String vnp_OrderInfo) {
        this.vnp_BankCode = vnp_BankCode;
        this.vnp_BankTranNo = vnpBankTranNo;
        this.vnp_CardType = vnp_CardType;
        this.vnp_PayDate = vnp_PayDate;
        this.vnp_ResponseCode = vnp_ResponseCode;
        this.vnp_SecureHash = vnp_SecureHash;
        this.vnp_TmnCode = vnp_TmnCode;
        this.vnp_TransactionNo = vnp_TransactionNo;
        this.vnp_TransactionStatus = vnp_TransactionStatus;
        this.vnp_TxnRef = vnp_TxnRef;
        this.vnp_Amount = new BigDecimal(vnp_Amount);
        this.vnp_OrderInfo = vnp_OrderInfo;
        return this;
    }

    public PaymentDTO paymentDTO(Payment payment) {
        this.id = payment.getId();
        this.vnp_BankCode = payment.getVnpBankCode();
        this.vnp_Amount = payment.getVnpAmount();
        this.vnp_BankTranNo = payment.getVnpBankTranNo();
        this.vnp_CardType = payment.getVnpCardType();
        this.vnp_OrderInfo = payment.getVnpOrderInfo();
        this.vnp_PayDate = payment.getVnpPayDate();
        this.vnp_ResponseCode = payment.getVnpResponseCode();
        this.vnp_TmnCode = payment.getVnpTmnCode();
        this.vnp_TransactionNo = payment.getVnpTransactionNo();
        this.vnp_TransactionStatus = payment.getVnpTransactionStatus();
        this.vnp_TxnRef = payment.getVnpTxnRef();
        this.vnp_SecureHash = payment.getVnpSecureHash();
        this.user_id = payment.getUserId();
        this.hook_status = payment.getHookStatus();
        return this;
    }
}
