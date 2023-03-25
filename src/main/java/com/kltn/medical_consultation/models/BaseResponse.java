package com.kltn.medical_consultation.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BaseResponse<T> {
    @Schema(defaultValue = "1" , description = "Mã code thành công hoặc mã lỗi của api")
    private int code = ERROR.SUCCESS.getCode();

    @Schema(defaultValue = "Message" , description = "message của server trả về")
    private String message;

    private String messageCode;

    private Object data;

    public BaseResponse(T data){
        this.data = data;
    }

    public BaseResponse(int code, String message, String messageCode){
        this.code = code;
        this.message = message;
        this.messageCode = messageCode;
    }

    public BaseResponse(String messageCode){
        this.messageCode = messageCode;
    }

    public BaseResponse(ERROR error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BaseResponse(IMessageCode iMessageCode, T data){
        this.data = data;
        this.messageCode = iMessageCode.getCode();
        this.message = iMessageCode.getMessage();
    }

    public BaseResponse(IMessageCode iMessageCode){
        this.messageCode = iMessageCode.getCode();
        this.message = iMessageCode.getMessage();
    }
}
