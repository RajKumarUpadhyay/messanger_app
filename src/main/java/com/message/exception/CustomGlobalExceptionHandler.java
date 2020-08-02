package com.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse();
        apiExceptionResponse.setMessage(ex.getMessage());
        apiExceptionResponse.setStatus(HttpStatus.NOT_FOUND);
        apiExceptionResponse.setLocalDate(LocalDate.now());
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceRequestException.class)
    public final ResponseEntity<ApiExceptionResponse> handleResourceNotFoundException(DuplicateResourceRequestException ex) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse();
        apiExceptionResponse.setMessage(ex.getMessage());
        apiExceptionResponse.setStatus(HttpStatus.CONFLICT);
        apiExceptionResponse.setLocalDate(LocalDate.now());
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiExceptionResponse> handleException(Exception ex) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse();
        System.out.println(apiExceptionResponse);
        apiExceptionResponse.setMessage(ex.getMessage());
        apiExceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiExceptionResponse.setLocalDate(LocalDate.now());
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}