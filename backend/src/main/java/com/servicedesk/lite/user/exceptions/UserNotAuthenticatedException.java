package com.servicedesk.lite.user.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAuthenticatedException extends RuntimeException {
    private final HttpStatus statusCode;

    public UserNotAuthenticatedException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
