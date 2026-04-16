package com.servicedesk.lite.user.exceptions;

public class AssigneeNotActiveException extends RuntimeException {
    public AssigneeNotActiveException(String message) {
        super(message);
    }
}
