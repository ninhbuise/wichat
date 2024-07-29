package com.wichat.service.rest;

import com.wichat.service.service.keycloak.KeycloakService;
import com.wichat.service.service.keycloak.RoleService;
import com.wichat.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo")
public class DemoController {

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hello() {
        return Utils.appendResponse(HttpStatus.OK, "Hello from Spring boot & Keycloak", "ROLE_USER");
    }

    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> hello2() {
        return Utils.appendResponse(HttpStatus.OK, "Hello from Spring boot & Keycloak - ADMIN", "ROLE_ADMIN");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersRealm() {
        var data = keycloakService.getUserResources();
        return Utils.appendResponse(HttpStatus.OK, "get user resources successfully", data);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getClientRoles() {
        var data = roleService.getClientRoles();
        return Utils.appendResponse(HttpStatus.OK, "get user resources successfully", data);
    }
}
