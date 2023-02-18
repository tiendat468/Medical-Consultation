package com.kltn.medical_consultation.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends  RuntimeException{
    private int errorCode;
    private String errorMsg;
    private HttpStatus httpStatus;
    private String messageCode;

    public ApiException(ERROR exception) {
        super(exception.getMessage());
        this.errorCode = exception.getCode();
        this.errorMsg = exception.getMessage();
        this.httpStatus = HttpStatus.OK;
    }

    public ApiException(ERROR exception, String errorMsg) {
        super(errorMsg);
        this.errorCode = exception.getCode();
        this.errorMsg = errorMsg;
        this.httpStatus = HttpStatus.OK;
    }

    public ApiException(IMessageCode iMessageCode) {
        super();
        this.errorMsg = iMessageCode.getMessage();
        this.httpStatus = HttpStatus.OK;
        this.messageCode = iMessageCode.getCode();
    }
}
