package com.servicedesk.lite.org.exception;

public class OrganizationSlugAlreadyExistsException extends RuntimeException {
    public OrganizationSlugAlreadyExistsException(String message) {
        super(message);
    }
}
