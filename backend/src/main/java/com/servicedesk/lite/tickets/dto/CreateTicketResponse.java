package com.servicedesk.lite.tickets.dto;

import java.util.UUID;

public class CreateTicketResponse {
    private UUID id;

    public CreateTicketResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
