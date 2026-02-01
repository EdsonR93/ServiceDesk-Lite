package com.servicedesk.lite.auth.jwt;

import com.servicedesk.lite.user.User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties props;

    public JwtService(JwtEncoder jwtEncoder, JwtProperties props) {
        this.jwtEncoder = jwtEncoder;
        this.props = props;
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(props.accessTokenTtlSeconds());

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(props.issuer())
            .issuedAt(now)
            .expiresAt(expiresAt)
            .subject(user.getId().toString())
            .claim("email", user.getEmail())
            .claim("status", user.getStatus().name())
            .claim("roles", List.of("ADMIN"))
            .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header,claims)).getTokenValue();
    }

    public long accessTokenTtlSeconds() {
        return props.accessTokenTtlSeconds();
    }
}
