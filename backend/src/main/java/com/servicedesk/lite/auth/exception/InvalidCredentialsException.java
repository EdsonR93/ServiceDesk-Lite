package com.servicedesk.lite.auth.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        //No args constructor to avoid leaking credentials information.
        super("Invalid Credentials were provided");
    }
}
