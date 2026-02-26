package com.servicedesk.lite.tickets.dto;

import java.util.UUID;

public class CreateCommentResponse {
    private final UUID id;

    public CreateCommentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
