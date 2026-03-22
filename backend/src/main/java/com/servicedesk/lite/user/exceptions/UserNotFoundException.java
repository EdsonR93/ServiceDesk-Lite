package com.servicedesk.lite.user.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {
    private final HttpStatus statusCode;

    public UserNotFoundException(String message) {
        super(message);
        this.statusCode = HttpStatus.NOT_FOUND;
    }

    public UserNotFoundException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
