package com.ivangeorgiev.domuapi.web.utils;

import com.ivangeorgiev.domuapi.core.exceptions.AlreadyExistsException;
import com.ivangeorgiev.domuapi.core.exceptions.BadRequestException;
import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiResponse<Object>> handleConflict(NotFoundException ex, WebRequest request) {
        return ApiResponseFactory.create(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    ResponseEntity<ApiResponse<Object>> handleConflict(AlreadyExistsException ex, WebRequest request) {
        return ApiResponseFactory.create(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ApiResponse<Object>> handleConflict(BadRequestException ex, WebRequest request) {
        return ApiResponseFactory.create(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ApiResponseFactory.create("Validation failed", HttpStatus.BAD_REQUEST, errors);
    }
}
