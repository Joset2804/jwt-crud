package com.example.jwtcrud.user.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserResource {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotNull
    @NotBlank
    @Size(max=9)
    private String phone;

    @NotNull
    @NotBlank
    @Size(max=50)
    @Email
    private String email;

}
