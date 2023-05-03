package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.models.vnpay.VnpayPaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vnpay_payment")
@Entity
public class VnpayPayment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "payment_id", insertable = false, updatable = false)
    private Long paymentId;

    public VnpayPayment(VnpayPaymentDTO dto) {
        this.vnp_ResponseId = dto.getVnp_ResponseId();
        this.vnp_Command = dto.getVnp_Command();
        this.vnp_ResponseCode = dto.getVnp_ResponseCode();
        this.vnp_Message = dto.getVnp_Message();
        this.vnp_TmnCode = dto.getVnp_TmnCode();
        this.vnp_TxnRef = dto.getVnp_TxnRef();
        this.vnp_Amount = dto.getVnp_Amount();
        this.vnp_OrderInfo = dto.getVnp_OrderInfo();
        this.vnp_BankCode = dto.getVnp_BankCode();
        this.vnp_PayDate = dto.getVnp_PayDate();
        this.vnp_TransactionNo = dto.getVnp_TransactionNo();
        this.vnp_TransactionType = dto.getVnp_TransactionType();
        this.vnp_TransactionStatus = dto.getVnp_TransactionStatus();
        this.vnp_CardNumber = dto.getVnp_CardNumber();
        this.vnp_Trace = dto.getVnp_Trace();
        this.vnp_CardHolder = dto.getVnp_CardHolder();
        this.vnp_FeeAmount = dto.getVnp_FeeAmount();
        this.vnp_SecureHash = dto.getVnp_SecureHash();
    }

    public static VnpayPayment of(VnpayPayment vnpayPayment, VnpayPaymentDTO dto) {
        vnpayPayment.setVnp_ResponseId(dto.getVnp_ResponseId());
        vnpayPayment.setVnp_Command(dto.getVnp_Command());
        vnpayPayment.setVnp_ResponseCode(dto.getVnp_ResponseCode());
        vnpayPayment.setVnp_Message(dto.getVnp_Message());
        vnpayPayment.setVnp_TmnCode(dto.getVnp_TmnCode());
        vnpayPayment.setVnp_TxnRef(dto.getVnp_TxnRef());
        vnpayPayment.setVnp_Amount(dto.getVnp_Amount());
        vnpayPayment.setVnp_OrderInfo(dto.getVnp_OrderInfo());
        vnpayPayment.setVnp_BankCode(dto.getVnp_BankCode());
        vnpayPayment.setVnp_PayDate(dto.getVnp_PayDate());
        vnpayPayment.setVnp_TransactionNo(dto.getVnp_TransactionNo());
        vnpayPayment.setVnp_TransactionType(dto.getVnp_TransactionType());
        vnpayPayment.setVnp_TransactionStatus(dto.getVnp_TransactionStatus());
        vnpayPayment.setVnp_CardNumber(dto.getVnp_CardNumber());
        vnpayPayment.setVnp_Trace(dto.getVnp_Trace());
        vnpayPayment.setVnp_CardHolder(dto.getVnp_CardHolder());
        vnpayPayment.setVnp_FeeAmount(dto.getVnp_FeeAmount());
        vnpayPayment.setVnp_SecureHash(dto.getVnp_SecureHash());
        return vnpayPayment;
    }
}
