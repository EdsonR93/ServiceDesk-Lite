package com.servicedesk.lite.user.exceptions;

import org.springframework.http.HttpStatus;

public class AssigneeNotActiveException extends RuntimeException {
    private final HttpStatus statusCode;

    public AssigneeNotActiveException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
