package com.wk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wk.domain.UserProfile;
import com.wk.exception.KeycloakUserCreationException;
import com.wk.mapper.UserProfileMapper;
import com.wk.property.KeycloakConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserService {

    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;
    private final UserProfileMapper mapper;
    private final ObjectMapper objectMapper;

    public void createKeycloakUser(UserProfile user, String password) {

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        // Set password
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        kcUser.setCredentials(List.of(credential));

        // Add custom attributes
        Map<String, List<String>> attributes = new HashMap<>();
        // todo remove
        attributes.put("permissions", List.of("dumm_permission_1", "dummy_permission_2"));
        try {
            var userClaimDto = mapper.toKeyCloakUserClaimDto(user);
            attributes.put("appUserInfo", List.of(objectMapper.writeValueAsString(userClaimDto)));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        kcUser.setAttributes(attributes);

        // Create user in Keycloak
        var realmResource = keycloak.realm(keycloakConfig.getTargetRealm());
        var usersResource = realmResource.users();
        try (var response = usersResource.create(kcUser)) {
            if (response.getStatus() == 201) {
                String locationHeader = response.getHeaderString("Location");
                String keycloakUserId = locationHeader.substring(
                    locationHeader.lastIndexOf('/') + 1);

                log.info("Successfully created Keycloak user: {}", user.getUsername());
            } else {
                String errorMessage = response.readEntity(String.class);
                log.error("Failed to create Keycloak user: {} - {}", response.getStatus(),
                    errorMessage);
                throw new KeycloakUserCreationException(
                    "Failed to create user in Keycloak: " + errorMessage);
            }

        } catch (Exception e) {
            log.error("Error creating Keycloak user: {}", e.getMessage());
            throw new KeycloakUserCreationException("Error creating user in Keycloak", e);
        }
    }

}