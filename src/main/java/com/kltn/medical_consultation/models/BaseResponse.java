package com.kltn.medical_consultation.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BaseResponse<T> {
    @Schema(defaultValue = "1" , description = "Mã code thành công hoặc mã lỗi của api")
    private int code  = ERROR.SUCCESS.getCode();

    @Schema(defaultValue = "Message" , description = "message của server trả về")
    private String message;

    private String messageCode;

    private Object data;

    public BaseResponse(T data){
        this.data = data;
    }

    public BaseResponse(){
        this.message = ERROR.SUCCESS.getMessage();
    }

    public BaseResponse(ERROR error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BaseResponse(ApiException apiException){
        this.code = apiException.getErrorCode();
        this.message = apiException.getErrorMsg();
        this.messageCode = apiException.getMessageCode();
    }

    public BaseResponse(int code, String message, String messageCode){
        this.code = code;
        this.message = message;
        this.messageCode = messageCode;
    }
}
