package com.wichat.service.service.impl;


import com.wichat.service.dto.LoginRequestDto;
import com.wichat.service.dto.UserRegistrationRecord;
import com.wichat.service.service.keycloak.KeycloakService;
import com.wichat.service.service.keycloak.RoleService;
import com.wichat.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final RestTemplate restTemplate;

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    @Value("${keycloak.uri.token}")
    private String keycloakTokenUri;

    @Value("${keycloak.uri.logout}")
    private String keycloakLogout;

    @Value("${keycloak.admin.client-realms}")
    private String clientRealms;

    @Value("${keycloak.client.client_id}")
    private String clientId;

    @Value("${keycloak.client.authorization-grant-type}")
    private String grantType;

    @Value("${keycloak.client.client_secret}")
    private String clientSecret;

    /**
     * login by using username and password to keycloak, and capturing token on response body
     *
     * @param loginRequest LoginRequestDto
     * @return keycloak token
     */
    public ResponseEntity<?> login(LoginRequestDto loginRequest) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", loginRequest.getUsername());
        map.add("password", loginRequest.getPassword());
        map.add("client_id", clientId);
        map.add("grant_type", grantType);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        var response = restTemplate.postForObject(keycloakTokenUri, request, HashMap.class);

        return Utils.appendResponse(HttpStatus.OK, "Login success", response);
    }

    /**
     * logout and disabling active token from keycloak
     *
     * @param refreshToken String
     */
    public ResponseEntity<?> logout(String refreshToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        var response = restTemplate.postForObject(keycloakLogout, request, HashMap.class);

        return Utils.appendResponse(HttpStatus.OK, "Logout success", response);
    }

    /**
     * handle service register user keycloak
     *
     * @param registerUser RegisterUserDto
     */
    public ResponseEntity<?> register(UserRegistrationRecord registerUser) {
        var response = this.keycloakService.createUser(registerUser);
        if (response == null) {
            return Utils.appendResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Occurrence error when register user to keycloak server", null);
        }
        roleService.assignRoleByUsername(response.username(), "user");
        return Utils.appendResponse(HttpStatus.OK, "register success", response);
    }

}
