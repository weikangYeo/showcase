package com.wk.controller;

import com.wk.mapper.UserProfileMapper;
import com.wk.model.UserProfileDto;
import com.wk.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
