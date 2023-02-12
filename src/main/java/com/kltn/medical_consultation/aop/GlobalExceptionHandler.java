package com.kltn.medical_consultation.aop;

import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.auth.AuthMessageCode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleCustomizedException(ApiException e) {
        if (StringUtils.isEmpty(e.getMessageCode())){
            e.setMessageCode(AuthMessageCode.UNKNOWN.getCode());
        }
        return new ResponseEntity<>(new BaseResponse(e), e.getHttpStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(
                new BaseResponse(
                        ERROR.INVALID_PARAM.getCode(),
                        ERROR.INVALID_PARAM.getMessage(),
                        AuthMessageCode.UNKNOWN.getCode()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
