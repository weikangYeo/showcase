package com.wk.controller;

import com.wk.domain.UserProfile;
import com.wk.mapper.UserProfileMapper;
import com.wk.model.UserProfileDto;
import com.wk.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserProfilesController {

    private final UserProfileService userProfileService;
    private final UserProfileMapper mapper;

    @GetMapping("{id}")
    public UserProfileDto getUser(@PathVariable String id) {
        return mapper.toDto(userProfileService.findUserProfileById(id));
    }

    @PostMapping
    // todo change reqBody dto to a separate DTO
    // todo change responseBody to a DTO
    // todo add PreAuthorize code ?
    public UserProfile getUser(@RequestBody UserProfileDto userProfileDto) {
        return userProfileService.createUserProfile(mapper.toEntity(userProfileDto));
    }
}
