package com.servicedesk.lite.auth.dto;

public class MeResponse {
    private String subject;
    private String email;
    private String status;

    public MeResponse(String subject, String email, String status) {
        this.status = status;
        this.email = email;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
