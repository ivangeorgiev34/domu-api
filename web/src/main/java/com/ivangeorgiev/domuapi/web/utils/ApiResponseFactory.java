package com.ivangeorgiev.domuapi.web.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ApiResponseFactory {

    public static <T> ResponseEntity<ApiResponse<T>> create(String message, HttpStatus status) {

        ApiResponse<T> responseData = new ApiResponse<>(message, null);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseData);
    }


    public static <T> ResponseEntity<ApiResponse<T>> create(String message, HttpStatus status, T data ) {

        ApiResponse<T> responseData = new ApiResponse<>(message, data);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseData);
    }
}
