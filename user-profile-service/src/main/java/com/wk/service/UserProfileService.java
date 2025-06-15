package com.wk.service;

import com.wk.domain.UserProfile;
import com.wk.repo.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile findUserProfileById(String id) {
        return userProfileRepository.findById(Long.valueOf(id))
            .orElseThrow(() ->  new EntityNotFoundException("User not found"));
    }
}
