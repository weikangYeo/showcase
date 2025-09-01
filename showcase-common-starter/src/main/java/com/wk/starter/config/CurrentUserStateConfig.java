package com.wk.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wk.starter.model.CurrentUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@Slf4j
public class CurrentUserStateConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @RequestScope
    public CurrentUserDto currentUserDto() {
        log.info("Creating User DTO");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
            || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            log.warn("Unauthorized or not a JWT token");
            return new CurrentUserDto();
        }

        return objectMapper.convertValue(jwt.getClaimAsMap("app-user-info"),  CurrentUserDto.class);
    }
}
