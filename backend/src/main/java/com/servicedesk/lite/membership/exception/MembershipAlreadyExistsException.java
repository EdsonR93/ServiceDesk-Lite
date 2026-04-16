package com.servicedesk.lite.membership.exception;

public class MembershipAlreadyExistsException extends RuntimeException {
    public MembershipAlreadyExistsException(String message) {
        super(message);
    }
}
