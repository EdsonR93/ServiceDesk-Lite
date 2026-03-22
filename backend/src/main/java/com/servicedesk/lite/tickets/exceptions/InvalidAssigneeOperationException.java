package com.servicedesk.lite.tickets.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidAssigneeOperationException extends RuntimeException {
    private final HttpStatus statusCode;

    public InvalidAssigneeOperationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
