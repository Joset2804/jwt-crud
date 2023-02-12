package com.example.jwtcrud.security.domain.service.communication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticateRequest {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
