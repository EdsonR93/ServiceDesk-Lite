package com.servicedesk.lite.org.dto;

import com.servicedesk.lite.membership.MembershipRole;

import java.util.UUID;

public class MembershipSummaryResponse {
    private final UUID userId;
    private final String email;
    private final MembershipRole role;

    public MembershipSummaryResponse(UUID userId, String email, MembershipRole role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public MembershipRole getRole() {
        return role;
    }
}
