package com.servicedesk.lite.auth;

import com.servicedesk.lite.auth.dto.LoginRequest;
import com.servicedesk.lite.auth.dto.LoginResponse;
import com.servicedesk.lite.auth.dto.RegisterRequest;
import com.servicedesk.lite.auth.exception.EmailAlreadyExistsException;
import com.servicedesk.lite.auth.exception.InvalidCredentialsException;
import com.servicedesk.lite.auth.jwt.JwtService;
import com.servicedesk.lite.user.Status;
import com.servicedesk.lite.user.User;
import com.servicedesk.lite.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Transactional
    public UUID register(RegisterRequest registerRequest) {

        String emailNormalized = registerRequest.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(emailNormalized)) {
            throw new EmailAlreadyExistsException(emailNormalized);
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        String fullName;
        if(registerRequest.getFullName() != null && !registerRequest.getFullName().trim().isEmpty()) {
            fullName = registerRequest.getFullName().trim();
        }else{
            fullName = null;
        }

        User newUser;

        newUser = userRepository.save(new User(emailNormalized, hashedPassword, fullName, Status.ACTIVE));
        return newUser.getId();

    }

    public LoginResponse login(LoginRequest loginRequest) {
        String emailNormalized = loginRequest.getEmail().trim().toLowerCase();
        Optional<User> optUser = userRepository.findByEmailIgnoreCase(emailNormalized);

        if (optUser.isPresent()) {
            User user = optUser.get();
            if (user.getStatus() == Status.ACTIVE) {
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                    String token = jwtService.generateAccessToken(user);
                    return new LoginResponse(token, "Bearer", jwtService.accessTokenTtlSeconds());
                }
            }
        }
        throw new InvalidCredentialsException();
    }
}
