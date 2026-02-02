package com.servicedesk.lite.auth.dto;

public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private long expiresInSeconds;

    public LoginResponse() {
        //Setting it to a default value
        this.tokenType = "Bearer";
        //Setting to 0 for now until we implement JWT
        this.expiresInSeconds = 0;
    }
    public LoginResponse(String accessToken, String tokenType, long expiresInSeconds) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }
}
