package com.kltn.medical_consultation.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class BasePaginationResponse<T>{

    @Schema(defaultValue = "1" , description = "Result Code")
    private int code;

    @Schema(defaultValue = "Message" , description = "Result message")
    private String message;

    @Schema(defaultValue = "MESSAGE_CODE" , description = "Message code")
    private String messageCode;

    private List<T> data;

    @Schema(description = "Number of total items")
    public long currentPage;

    @Schema(description = "Page index (begin = 0)")
    public int pageIndex;

    @Schema(description = "Page size (number of items per page)")
    public int pageSize;

    public BasePaginationResponse(List<T> data, int pageIndex, int pageSize, long currentPage){
        this.data = data;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }
}