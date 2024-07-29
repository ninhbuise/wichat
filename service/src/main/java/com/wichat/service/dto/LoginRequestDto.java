package com.wichat.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "username not blank")
    private String username;

    @NotBlank(message = "password not blank")
    private String password;

    @NotBlank(message = "reCaptcha not blank")
    private String reCaptcha;
}
