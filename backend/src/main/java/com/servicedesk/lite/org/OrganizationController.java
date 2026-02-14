package com.servicedesk.lite.org;

import com.servicedesk.lite.org.dto.CreateOrganizationRequest;
import com.servicedesk.lite.org.dto.CreateOrganizationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrganizationResponse createOrganization(@Valid @RequestBody CreateOrganizationRequest req, @AuthenticationPrincipal Jwt jwt) {
        UUID creatorId = UUID.fromString(jwt.getSubject());
        UUID orgId = organizationService.createOrganization(req, creatorId);
        return new CreateOrganizationResponse(orgId);
    }
}
