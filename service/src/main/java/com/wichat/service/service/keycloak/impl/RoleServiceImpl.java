package com.wichat.service.service.keycloak.impl;

import com.wichat.service.service.keycloak.KeycloakService;
import com.wichat.service.service.keycloak.RoleService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final Keycloak keycloak;

    private final KeycloakService keycloakService;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource userResource = keycloakService.getUserResource(userId);
        if (userResource == null) {
            return;
        }
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    @Override
    public void assignRoleByUsername(String username, String roleName) {
        UserResource userResource = keycloakService.getUserResourceByUsername(username);
        if (userResource == null) {
            return;
        }
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    @Override
    public List<RoleRepresentation> getClientRoles() {
        return getRolesResource().list();
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}
