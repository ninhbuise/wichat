package com.wichat.service.service.keycloak;

import com.wichat.service.dto.UserRegistrationRecord;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakService {

    UserRegistrationRecord createUser(UserRegistrationRecord registerUser);

    UserRepresentation getUserById(String userId);

    void deleteUserById(String userId);

    void emailVerification(String userId);

    UserResource getUserResource(String userId);

    List<UserRepresentation> getUserResources();

    UserResource getUserResourceByUsername(String username);

    void updatePassword(String userId);
}
