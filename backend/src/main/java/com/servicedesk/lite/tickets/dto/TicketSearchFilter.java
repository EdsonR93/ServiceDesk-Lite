package com.servicedesk.lite.tickets.dto;

import com.servicedesk.lite.tickets.TicketStatus;

import java.util.UUID;

public class TicketSearchFilter {

    private TicketStatus status;
    private UUID assigneeUserId;

    public TicketSearchFilter(TicketStatus status, UUID assigneeUserId) {
        this.status = status;
        this.assigneeUserId = assigneeUserId;
    }


    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public UUID getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(UUID assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }
}
