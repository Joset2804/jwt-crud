package com.example.jwtcrud.user.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResource {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private List<String> roles;
}
