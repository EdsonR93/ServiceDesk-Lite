package com.servicedesk.lite.org;

import com.servicedesk.lite.membership.Membership;
import com.servicedesk.lite.membership.MembershipRepository;
import com.servicedesk.lite.membership.MembershipRole;
import com.servicedesk.lite.org.dto.AddMemberRequest;
import com.servicedesk.lite.org.exception.OrgForbiddenException;
import com.servicedesk.lite.org.exception.UserNotFoundException;
import com.servicedesk.lite.user.User;
import com.servicedesk.lite.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;


    public MembershipService(MembershipRepository membershipRepository, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID addMember(UUID orgId, UUID callerUserId, AddMemberRequest req) {
        Optional<Membership> membershipOpt = membershipRepository.findByUserIdAndOrgId(callerUserId, orgId);
        Membership callerMembership = membershipOpt.orElseThrow((() -> new OrgForbiddenException("Not an org admin")));
        if (callerMembership.getRole() != MembershipRole.ADMIN) {
            throw new OrgForbiddenException("Not an org admin");
        }

        String email = req.getEmail().trim().toLowerCase();
        Optional<User> targetUserOpt = userRepository.findByEmailIgnoreCase(email);
        User targetUser = targetUserOpt.orElseThrow((() -> new UserNotFoundException("User with email: " + email + " not found")));

        Membership newMembership = membershipRepository.save(new Membership(orgId, targetUser.getId(), req.getRole()));
        return newMembership.getId();
    }
}
