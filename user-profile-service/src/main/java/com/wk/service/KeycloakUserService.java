package com.wk.service;

import com.wk.domain.UserProfile;
import com.wk.exception.KeycloakUserCreationException;
import com.wk.property.KeycloakConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserService {

    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;

    public String createKeycloakUser(UserProfile user, String password) {

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getName());
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
        // todo probably role and permission populate here
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("department", List.of("testDepartment1", "testDepartment2"));
        attributes.put("permissions", List.of("dummy_permission_1", "dummy_permission_2"));
        kcUser.setAttributes(attributes);

        // Create user in Keycloak
        var realmResource = keycloak.realm(keycloakConfig.getTargetRealm());
        var usersResource = realmResource.users();
        try (var response = usersResource.create(kcUser)) {
            if (response.getStatus() == 201) {
                String locationHeader = response.getHeaderString("Location");
                String keycloakUserId = locationHeader.substring(
                    locationHeader.lastIndexOf('/') + 1);

                // Assign default roles
                assignDefaultRoles(keycloakUserId, user.getRole());

                log.info("Successfully created Keycloak user: {}", user.getName());
                return keycloakUserId;
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

    private void assignDefaultRoles(String keycloakUserId, String userRole) {
        try {
            var realmResource = keycloak.realm(keycloakConfig.getTargetRealm());
            var userResource = realmResource.users().get(keycloakUserId);

            // Get realm roles
            RoleRepresentation roleRepresentation = realmResource.roles().get(userRole)
                .toRepresentation();

            // Assign role to user
            userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
            log.info("Assigned role '{}' to user: {}", userRole, keycloakUserId);
        } catch (Exception e) {
            log.error("Error assigning role to user: {}", e.getMessage());
        }
    }
}