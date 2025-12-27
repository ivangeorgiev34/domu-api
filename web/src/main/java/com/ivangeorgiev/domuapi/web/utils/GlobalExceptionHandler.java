package com.ivangeorgiev.domuapi.web.utils;

import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiResponse<Object>> handleConflict(NotFoundException ex, WebRequest request) {
        return ApiResponseFactory.create(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
