package com.servicedesk.lite.tickets.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedCommentAuthorException extends RuntimeException {
    public UnauthorizedCommentAuthorException(String message, HttpStatus statusCode) {
        super(message);
    }
}
