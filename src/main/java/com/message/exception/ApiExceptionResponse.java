package com.message.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class ApiExceptionResponse {

    private HttpStatus status;
    private String message;

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    private LocalDate localDate;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}