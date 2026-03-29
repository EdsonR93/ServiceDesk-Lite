package com.servicedesk.lite.user;

import com.servicedesk.lite.membership.MembershipRepository;
import com.servicedesk.lite.user.exceptions.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserValidator {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public UserValidator(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public User requireActiveCreator(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new UserNotAuthenticatedException("User not authenticated"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new UserInactiveException("User is inactive");
        }

        return user;
    }

    public User requireAssignableUser(UUID orgId, UUID assigneeUserId) {
        if (!membershipRepository.existsByOrgIdAndUserId(orgId, assigneeUserId)) {
            throw new UserNotInOrganizationException("User not allowed to assign outside organization");
        }
        Optional<User> userOpt = userRepository.findById(assigneeUserId);

        User user = userOpt.orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new AssigneeNotActiveException("Assignee must be ACTIVE");
        }

        return user;
    }
}
