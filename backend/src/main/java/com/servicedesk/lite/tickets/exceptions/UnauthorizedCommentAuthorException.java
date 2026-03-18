package com.servicedesk.lite.tickets.exceptions;

public class UnauthorizedCommentAuthorException extends RuntimeException {
    public UnauthorizedCommentAuthorException(String message) {
        super(message);
    }
}
