package com.servicedesk.lite.org.dto;

import java.util.UUID;

public class CreateOrganizationResponse {
    private final UUID id;

    public CreateOrganizationResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
