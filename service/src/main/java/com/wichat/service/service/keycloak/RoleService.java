package com.wichat.service.service.keycloak;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface RoleService {
    void assignRole(String userId, String roleName);

    void assignRoleByUsername(String username, String roleName);

    List<RoleRepresentation> getClientRoles();
}
