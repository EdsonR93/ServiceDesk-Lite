package com.servicedesk.lite.tickets.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTicketUpdateException extends RuntimeException {
    private final HttpStatus statusCode;

    public InvalidTicketUpdateException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
