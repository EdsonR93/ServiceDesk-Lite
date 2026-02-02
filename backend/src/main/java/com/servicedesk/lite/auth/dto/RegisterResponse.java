package com.servicedesk.lite.auth.dto;

import java.util.UUID;

public class RegisterResponse {
    private final UUID userId;

    public RegisterResponse(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
