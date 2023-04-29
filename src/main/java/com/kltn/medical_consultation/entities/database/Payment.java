package com.kltn.medical_consultation.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kltn.medical_consultation.models.vnpay.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Entity
public class Payment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vnpBankCode;
    private BigDecimal vnpAmount;
    private String vnpBankTranNo;
    private String vnpCardType;
    private String vnpOrderInfo;
    private String vnpPayDate;
    private String vnpResponseCode;
    private String vnpTmnCode;
    private String vnpTransactionNo;
    private String vnpTransactionStatus;
    private String vnpTxnRef;
    private String vnpSecureHash;
    private Long userId;
    private Boolean hookStatus = false;

    @JsonIgnore
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<VnpayPayment> vnpayPayments;


    public Payment payment(PaymentDTO paymentDTO){
        this.userId = paymentDTO.getUser_id();
        this.vnpAmount = paymentDTO.getVnp_Amount();
        this.vnpBankCode = paymentDTO.getVnp_BankCode();
        this.vnpOrderInfo = paymentDTO.getVnp_OrderInfo();
        this.vnpBankTranNo = paymentDTO.getVnp_BankTranNo();
        this.vnpCardType = paymentDTO.getVnp_CardType();
        this.vnpPayDate = paymentDTO.getVnp_PayDate();
        this.vnpResponseCode = paymentDTO.getVnp_ResponseCode();
        this.vnpSecureHash = paymentDTO.getVnp_SecureHash();
        this.vnpTmnCode = paymentDTO.getVnp_TmnCode();
        this.vnpTransactionNo = paymentDTO.getVnp_TransactionNo();
        this.vnpTransactionStatus = paymentDTO.getVnp_TransactionStatus();
        this.vnpTxnRef = paymentDTO.getVnp_TxnRef();
        return this;
    }

    public Payment(PaymentDTO paymentDTO) {
        this.userId = paymentDTO.getUser_id();
        this.vnpAmount = paymentDTO.getVnp_Amount();
        this.vnpBankCode = paymentDTO.getVnp_BankCode();
        this.vnpOrderInfo = paymentDTO.getVnp_OrderInfo();
        this.vnpBankTranNo = paymentDTO.getVnp_BankTranNo();
        this.vnpCardType = paymentDTO.getVnp_CardType();
        this.vnpPayDate = paymentDTO.getVnp_PayDate();
        this.vnpResponseCode = paymentDTO.getVnp_ResponseCode();
        this.vnpSecureHash = paymentDTO.getVnp_SecureHash();
        this.vnpTmnCode = paymentDTO.getVnp_TmnCode();
        this.vnpTransactionNo = paymentDTO.getVnp_TransactionNo();
        this.vnpTransactionStatus = paymentDTO.getVnp_TransactionStatus();
        this.vnpTxnRef = paymentDTO.getVnp_TxnRef();
    }
}