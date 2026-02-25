package com.servicedesk.lite.tickets.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class CreateTicketRequest {
    @NotBlank
    private String title;

    private String description;

    private UUID assigneeUserId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(UUID assignedUserId) {
        this.assigneeUserId = assignedUserId;
    }
}
