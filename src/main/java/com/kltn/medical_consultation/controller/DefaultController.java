package com.kltn.medical_consultation.controller;

import com.kltn.medical_consultation.models.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noauth/api/v1")
public class DefaultController {

    @Operation(summary = "API này là api mặc định",
            description = "API này là api mặc định - dùng để trả về mặc định",
            tags = {"Controller mặc định"})
    @ApiResponse(responseCode = "200", description = "Thành công",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BaseResponse.class))})

    @RequestMapping(method = {RequestMethod.GET , RequestMethod.POST,
            RequestMethod.PUT ,  RequestMethod.DELETE })
    public BaseResponse defaultApi(){

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(1);
        baseResponse.setMessage("Hello world");
        return baseResponse;
    }
}
