package com.servicedesk.lite.org.exception;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
