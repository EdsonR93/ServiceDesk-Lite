package com.servicedesk.lite.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.security.jwt")
public record JwtProperties(
    String issuer,
    long accessTokenTtlSeconds,
    String secretBase64
) {}
