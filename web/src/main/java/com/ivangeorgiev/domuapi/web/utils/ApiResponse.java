package com.ivangeorgiev.domuapi.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    public ApiResponse(String message, T data){
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message){
        this.message = message;
    }

    private String message;
    private T data;
}
