package com.servicedesk.lite.org.dto;

import com.servicedesk.lite.membership.MembershipRole;

import java.util.UUID;

public class OrganizationSummaryResponse {
    private final UUID id;
    private final String name;
    private final String slug;
    private final MembershipRole role;

    public OrganizationSummaryResponse(UUID id, String name, String slug, MembershipRole role) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public MembershipRole getRole() {
        return role;
    }
}
