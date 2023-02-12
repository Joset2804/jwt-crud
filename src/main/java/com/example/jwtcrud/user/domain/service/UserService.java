package com.example.jwtcrud.user.domain.service;

import com.example.jwtcrud.security.domain.service.communication.AuthenticateRequest;
import com.example.jwtcrud.user.domain.model.entity.User;
import com.example.jwtcrud.user.domain.service.communication.RegisterUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAll();
    ResponseEntity<?> authenticate(AuthenticateRequest request);
    ResponseEntity<?> register(RegisterUserRequest user);
    User getById(Long userId);
    User create(User user);
    User update(Long userId, User user);
    ResponseEntity<?> delete(Long userId);

}
