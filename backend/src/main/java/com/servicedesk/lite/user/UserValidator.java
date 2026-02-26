package com.servicedesk.lite.user;

import com.servicedesk.lite.membership.MembershipRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

public class UserValidator {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public UserValidator(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public User requireActiveCreator(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        return user;
    }

    public User requireAssignableUser(UUID orgId, UUID assigneeUserId) {
        if (!membershipRepository.existsByOrgIdAndUserId(orgId, assigneeUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed to assign outside organization");
        }
        Optional<User> userOpt = userRepository.findById(assigneeUserId);

        User user = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignee must be ACTIVE");
        }

        return user;
    }
}
