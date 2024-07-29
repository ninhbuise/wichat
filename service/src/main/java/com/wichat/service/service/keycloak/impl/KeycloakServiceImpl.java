package com.wichat.service.service.keycloak.impl;

import com.wichat.service.dto.UserRegistrationRecord;
import com.wichat.service.service.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client.email-verified}")
    private boolean emailVerified;

    @Override
    public UserRegistrationRecord createUser(UserRegistrationRecord registerUser) {
        log.debug("create user -> {}", registerUser);
        UserRepresentation user = createUserRepresentation(registerUser);
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(user);
        log.debug("Keycloak response -> status: {} - metaData: {}", response.getStatus(), response.getMetadata());

        if (Objects.equals(HttpStatus.CREATED.value(), response.getStatus())) {
            List<UserRepresentation> representationList = usersResource.searchByUsername(registerUser.username(), true);
            if (!CollectionUtils.isEmpty(representationList)) {
                UserRepresentation userVerifyEmail = representationList
                        .stream()
                        .filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified()))
                        .findFirst().orElse(null);
                assert userVerifyEmail != null;
                emailVerification(userVerifyEmail.getId());
            }
            return registerUser;
        }
        return null;
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
    }

    @Override
    public void emailVerification(String userId) {
        getUsersResource().get(userId).sendVerifyEmail();
    }

    @Override
    public UserResource getUserResource(String userId) {
        return getUsersResource().get(userId);
    }

    @Override
    public List<UserRepresentation> getUserResources() {
        return getUsersResource().list();
    }

    @Override
    public UserResource getUserResourceByUsername(String username) {
        List<UserRepresentation> representationList = getUsersResource().searchByUsername(username, true);
        if (CollectionUtils.isEmpty(representationList)) {
            return null;
        }
        return (UserResource) representationList.stream().findFirst().orElse(null);
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions = List.of("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }

    private UserRepresentation createUserRepresentation(UserRegistrationRecord userRegistrationRecord) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.username());
        user.setEmail(userRegistrationRecord.email());
        user.setFirstName(userRegistrationRecord.firstName());
        user.setLastName(userRegistrationRecord.lastName());
        user.setEmailVerified(emailVerified);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        user.setCredentials(List.of(credentialRepresentation));
        return user;
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }
}
