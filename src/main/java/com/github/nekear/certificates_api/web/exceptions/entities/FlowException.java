package com.github.nekear.certificates_api.web.exceptions.entities;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FlowException extends RuntimeException {
    private final HttpStatus status;

    public FlowException(String message, HttpStatus status) {
        this(message, status, null);
    }

    public FlowException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
