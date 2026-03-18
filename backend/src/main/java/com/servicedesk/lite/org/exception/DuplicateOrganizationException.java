package com.servicedesk.lite.org.exception;

import org.springframework.http.HttpStatus;

public class DuplicateOrganizationException extends RuntimeException {
    private final HttpStatus statusCode;

    public DuplicateOrganizationException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
