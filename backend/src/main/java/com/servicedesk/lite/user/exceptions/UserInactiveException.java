package com.servicedesk.lite.user.exceptions;

import org.springframework.http.HttpStatus;

public class UserInactiveException extends RuntimeException {
    private final HttpStatus statusCode;

    public UserInactiveException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
