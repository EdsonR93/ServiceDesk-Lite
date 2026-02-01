package com.servicedesk.lite.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@Profile("dev")
@RestController
@RequestMapping("/api/auth")
public class DebugAuthController {
    @GetMapping("/roles-check")
    public Map<String, Object> rolesCheck(@AuthenticationPrincipal Jwt jwt,Authentication auth){
        Map<String,Object> map = new HashMap<>();
        map.put("sub", jwt.getSubject());
        map.put("email", jwt.getClaimAsString("email"));
        map.put("rolesClaim", jwt.getClaimAsStringList("roles"));
        map.put("authorities", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return map;
    }

    @GetMapping("/admin-check")
    public Map<String, Object> adminCheck( @AuthenticationPrincipal Jwt jwt, Authentication auth){
        return Map.of("OK",true);
    }
}
