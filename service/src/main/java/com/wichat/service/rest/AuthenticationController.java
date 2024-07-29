package com.wichat.service.rest;

import com.wichat.service.dto.LoginRequestDto;
import com.wichat.service.dto.UserRegistrationRecord;
import com.wichat.service.service.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    /**
     * login by username & password keycloak handler
     *
     * @param request LoginRequestDto
     * @return token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        return this.authenticationService.login(request);
    }

    /**
     * logout and disabling active token from keycloak
     *
     * @param refreshToken String
     */
    @GetMapping("/logout/{refreshToken}")
    public ResponseEntity<?> logout(@PathVariable String refreshToken) {
        return this.authenticationService.logout(refreshToken);
    }

    /**
     * register user into keycloak
     *
     * @param registerUser RegisterUserDto
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRecord registerUser) {
        return this.authenticationService.register(registerUser);
    }
}
