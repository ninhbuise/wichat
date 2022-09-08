package com.wichat.authentication.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;

}