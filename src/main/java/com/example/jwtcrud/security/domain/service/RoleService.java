package com.example.jwtcrud.security.domain.service;

import com.example.jwtcrud.security.domain.model.entity.Role;

import java.util.List;

public interface RoleService {

    void seed();
    List<Role> getAll();
}
