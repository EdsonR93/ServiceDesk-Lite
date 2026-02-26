package com.servicedesk.lite.tickets.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {
    @NotBlank
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
