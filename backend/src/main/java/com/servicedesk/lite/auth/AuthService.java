package com.servicedesk.lite.auth;

import com.servicedesk.lite.auth.dto.RegisterRequest;
import com.servicedesk.lite.auth.exception.EmailAlreadyExistsException;
import com.servicedesk.lite.user.Status;
import com.servicedesk.lite.user.User;
import com.servicedesk.lite.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}
