package com.example.jwtcrud.user.resource;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class AuthenticateUserResource {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private List<String> roles;
    private String token;
}
