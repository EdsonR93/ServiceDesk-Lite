package com.servicedesk.lite.tickets.exceptions;

public class InvalidTicketUpdateException extends RuntimeException {
    public InvalidTicketUpdateException(String message) {
        super(message);
    }
}
