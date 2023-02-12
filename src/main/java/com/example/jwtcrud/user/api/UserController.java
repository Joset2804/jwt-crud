package com.example.jwtcrud.user.api;

import com.example.jwtcrud.security.domain.service.communication.AuthenticateRequest;
import com.example.jwtcrud.user.domain.service.UserService;
import com.example.jwtcrud.user.domain.service.communication.RegisterUserRequest;
import com.example.jwtcrud.user.mapping.UserMapper;
import com.example.jwtcrud.user.resource.UpdateUserResource;
import com.example.jwtcrud.user.resource.UserResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserResource> getAllUsers() {
        return userMapper.modelListToResource(userService.getAll());
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResource getUserById(@PathVariable("userId") Long userId) {
        return userMapper.toResource(userService.getById(userId));
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateRequest request) {
        return userService.authenticate(request);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return userService.register(request);
    }

    @PutMapping("{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResource updateUser(@PathVariable Long userId, @RequestBody UpdateUserResource request) {
        return userMapper.toResource(userService.update(userId, userMapper.toModel(request)));
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userService.delete(userId);
    }
}
