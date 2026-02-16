package com.servicedesk.lite.org.dto;

import com.servicedesk.lite.membership.MembershipRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddMemberRequest {
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    private MembershipRole role;

    public AddMemberRequest() {
    }

    public AddMemberRequest(String email, MembershipRole role) {
        this.email = email;
        this.role = role;
    }

    public void setRole(MembershipRole role) {
        this.role = role;
    }

    public MembershipRole getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
