package com.wk.property;

import lombok.Data;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// todo add to archetype
@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Data
public class KeycloakConfig {
    private Admin admin;
    private String targetRealm;

    @Data
    public static class Admin {
        private String serverUrl;
        private String realm;
        private String clientId;
        private String clientSecret;
    }

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
            .serverUrl(admin.getServerUrl())
            .realm(admin.getRealm())
            .clientId(admin.getClientId())
            .clientSecret(admin.getClientSecret())
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .build();
    }
}