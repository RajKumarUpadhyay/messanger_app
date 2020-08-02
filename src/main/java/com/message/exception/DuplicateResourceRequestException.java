package com.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceRequestException extends RuntimeException {
    public DuplicateResourceRequestException(String message) {
        super(message);
    }

    public DuplicateResourceRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
