package com.servicedesk.lite.tickets.dto;

import com.servicedesk.lite.tickets.TicketStatus;

import java.util.UUID;

public class UpdateTicketRequest {
    private String title;
    private String description;
    private TicketStatus status;
    private UUID assigneeUserId;
    private Boolean clearAssignee;


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

    public void setAssigneeUserId(UUID assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Boolean isClearAssignee() {
        return clearAssignee;
    }

    public void setClearAssignee(Boolean clearAssignee) {
        this.clearAssignee = clearAssignee;
    }
}
