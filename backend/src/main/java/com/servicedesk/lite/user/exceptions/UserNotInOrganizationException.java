package com.servicedesk.lite.user.exceptions;

public class UserNotInOrganizationException extends RuntimeException {
    public UserNotInOrganizationException(String message) {
        super(message);
    }
}
