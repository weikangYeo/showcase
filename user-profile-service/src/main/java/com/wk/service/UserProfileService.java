package com.wk.service;

import com.wk.domain.UserProfile;
import com.wk.repo.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final KeycloakUserService keycloakUserService;
    private final RoleService roleService;

    public UserProfile findUserProfileById(String id) {
        return userProfileRepository.findById(Long.valueOf(id))
            .orElseThrow(() ->  new EntityNotFoundException("User not found"));
    }

    @Transactional
    public UserProfile createUserProfile(UserProfile userProfile) {
        userProfile.setFirstName(userProfile.getUsername());
        userProfile.setLastName(userProfile.getUsername());
        var defaultRole = roleService.findRoleById(1L);
        userProfile.setRoles(Set.of(defaultRole));
        var result = userProfileRepository.save(userProfile);

        keycloakUserService.createKeycloakUser(result, "test");
        return result;
    }
}
