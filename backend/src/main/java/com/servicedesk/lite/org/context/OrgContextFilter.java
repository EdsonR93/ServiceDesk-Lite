package com.servicedesk.lite.org.context;

import com.servicedesk.lite.membership.MembershipRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class OrgContextFilter extends OncePerRequestFilter {
    private final MembershipRepository membershipRepository;

    public OrgContextFilter(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String orgHeader = request.getHeader("X-Org-Id");

        if (orgHeader == null || orgHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        UUID orgId;

        try {
            orgId = UUID.fromString(orgHeader);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Invalid X-Org-Id\"}");
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || !(auth instanceof JwtAuthenticationToken jwtAuth)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Authentication required\"}");
            return;
        }

        UUID callerId = UUID.fromString(jwtAuth.getToken().getSubject());

        if (!membershipRepository.existsByOrgIdAndUserId(orgId, callerId)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Not an org member\"}");
            return;
        }

        try {
            OrgContext.setOrgId(orgId);
            filterChain.doFilter(request, response);
        } finally {
            OrgContext.clear();
        }
    }
}
