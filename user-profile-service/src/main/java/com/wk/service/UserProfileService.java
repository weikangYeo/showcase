package com.wk.service;

import com.wk.domain.UserProfile;
import com.wk.repo.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final KeycloakUserService keycloakUserService;

    public UserProfile findUserProfileById(String id) {
        return userProfileRepository.findById(Long.valueOf(id))
            .orElseThrow(() ->  new EntityNotFoundException("User not found"));
    }

    @Transactional
    public UserProfile createUserProfile(UserProfile userProfile) {
        // todo change to something meaningful
        userProfile.setRole("TEST");
        var result = userProfileRepository.save(userProfile);
        keycloakUserService.createKeycloakUser(result, "test");
        return result;
    }
}
