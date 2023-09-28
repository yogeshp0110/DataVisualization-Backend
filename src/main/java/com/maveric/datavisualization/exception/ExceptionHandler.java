package com.maveric.datavisualization.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class ExceptionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})

    public Map<String, String> handleInvalidArgumentException(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap();

        ex.getBindingResult().getFieldErrors().forEach(error -> {

            errorMap.put(error.getField(), error.getDefaultMessage());

        });

        return errorMap;

    }

    @org.springframework.web.bind.annotation.ExceptionHandler({CustomExceptions.class})
    public ResponseEntity<ApiErrorResponse> handleCustomApiException(CustomExceptions ex, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(ex.getStatus(), ex.getError(), ex.getMessage(), request.getRequestURI(), ex.getLocalDate());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

}

