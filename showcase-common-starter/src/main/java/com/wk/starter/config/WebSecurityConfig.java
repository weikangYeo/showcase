package com.wk.starter.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * <pre>
 *
 * Have to declare 2 separate bean is because,  When you do specify management.server.port=9090,
 * Spring Boot fundamentally changes its behavior. It now starts two separate, independent embedded
 * web servers in the same application. <br/>
 * The management server on port 9090, having no specific
 * configuration applied to it, will fall back to Spring Security's default behavior, which is to
 * protect all endpoints.
 *   The result would be:
 *   http://localhost:8080/users/1 -> Secured (Correct)
 *   http://localhost:8080/actuator/health -> Permitted (Correct, because this rule is applied to the server on 8080)
 *   http://localhost:9090/actuator/health -> 401 UNAUTHORIZED (Incorrect!)
 * </pre>
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    @Order(1)
    public DefaultSecurityFilterChain managementSecurityFilterChain(HttpSecurity http)
        throws Exception {
        return http.securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
    }

    @Bean
    @Order(2)
    public DefaultSecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .build();
    }
}
