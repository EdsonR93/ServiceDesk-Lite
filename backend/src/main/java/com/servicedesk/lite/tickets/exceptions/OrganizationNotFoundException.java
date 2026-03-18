package com.servicedesk.lite.tickets.exceptions;

import org.springframework.http.HttpStatus;

public class OrganizationNotFoundException extends RuntimeException {
    private final HttpStatus statusCode;

    public OrganizationNotFoundException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
