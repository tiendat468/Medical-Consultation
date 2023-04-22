package com.kltn.medical_consultation.models.vnpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryDrDTO {
    private String vnp_RequestId;
    private String vnp_Version;
    private String vnp_Command;
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private String vnp_OrderInfo;
    private String vnp_TransactionDate;
    private String vnp_CreateDate;
    private String vnp_IpAddr;
    private String vnp_SecureHash;
    private String vnp_TransactionNo;


    public Object queryDTO(List<Values> values){
        this.vnp_RequestId = values.get(0).getValue();
        this.vnp_Version = values.get(1).getValue();
        this.vnp_Command = values.get(2).getValue();
        this.vnp_TmnCode = values.get(3).getValue();
        this.vnp_TxnRef = values.get(4).getValue();
        this.vnp_TransactionDate = values.get(5).getValue();
        this.vnp_CreateDate = values.get(6).getValue();
        this.vnp_IpAddr = values.get(7).getValue();
        this.vnp_OrderInfo = values.get(8).getValue();
        return this;
    }
}
