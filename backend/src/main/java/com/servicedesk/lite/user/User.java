package com.servicedesk.lite.user;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="email",nullable = false, unique = true, length = 320)
    private String email;

    @Column(name="password_hash", length = 255)
    private String passwordHash;

    @Column(name="full_name", length = 200)
    private String fullName;

    @Column(name="status",nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="created_at", insertable = false,updatable = false)
    private OffsetDateTime createdAt;

    @Column(name="updated_at", insertable = false,updatable = false)
    private OffsetDateTime updatedAt;

    protected User() {}
    public User(String email, String passwordHash, String fullName, Status status) {

        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
