package com.maveric.datavisualization.exception;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomExceptions extends RuntimeException {
    private final int status;
    private final String error;
    private final String path;
    private final String message;
    private final LocalDate localDate;

    public CustomExceptions(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.message = message;
        this.localDate = LocalDate.now();
    }




}
