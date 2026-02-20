package com.servicedesk.lite.config;

import com.servicedesk.lite.membership.MembershipRepository;
import com.servicedesk.lite.org.context.OrgContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // default strength is fine now
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationConverter jwtAuthConverter, OrgContextFilter orgContextFilter) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
                .anyRequest().permitAll() // later -> authenticated()
            ).oauth2ResourceServer((oauth2ResourceServer) ->
                oauth2ResourceServer
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
            .addFilterAfter(orgContextFilter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public OrgContextFilter getOrgContextFilter(MembershipRepository membershipRepository) {
        return new OrgContextFilter(membershipRepository);
    }
}
