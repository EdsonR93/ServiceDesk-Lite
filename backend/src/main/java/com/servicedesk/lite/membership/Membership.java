package com.servicedesk.lite.membership;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="org_id",nullable = false)
    private UUID orgId;

    @Column(name="user_id",nullable = false)
    private UUID userId;

    @Column(name="role",nullable = false,length=20)
    @Enumerated(EnumType.STRING)
    private MembershipRole role;

    @Column(name="created_at", insertable = false,updatable = false)
    private OffsetDateTime createdAt;

    public Membership() {}

    public Membership(UUID id, UUID orgId, UUID userId, MembershipRole role) {
        this.id = id;
        this.orgId = orgId;
        this.userId = userId;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrgId() {
        return orgId;
    }

    public void setOrgId(UUID orgId) {
        this.orgId = orgId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public MembershipRole getRole() {
        return role;
    }

    public void setRole(MembershipRole role) {
        this.role = role;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
