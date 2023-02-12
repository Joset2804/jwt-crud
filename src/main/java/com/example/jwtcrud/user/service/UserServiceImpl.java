package com.example.jwtcrud.user.service;

import com.example.jwtcrud.security.domain.model.entity.Role;
import com.example.jwtcrud.security.domain.model.enumeration.Roles;
import com.example.jwtcrud.security.domain.persistence.RoleRepository;
import com.example.jwtcrud.security.domain.service.communication.AuthenticateRequest;
import com.example.jwtcrud.shared.exception.ResourceNotFoundException;
import com.example.jwtcrud.shared.exception.ResourceValidationException;
import com.example.jwtcrud.shared.mapping.EnhancedModelMapper;
import com.example.jwtcrud.user.domain.model.entity.User;
import com.example.jwtcrud.user.domain.persistence.UserRepository;
import com.example.jwtcrud.user.domain.service.UserService;
import com.example.jwtcrud.user.domain.service.communication.AuthenticateUserResponse;
import com.example.jwtcrud.user.domain.service.communication.RegisterUserRequest;
import com.example.jwtcrud.user.domain.service.communication.RegisterUserResponse;
import com.example.jwtcrud.user.middleware.JwtHandlerUser;
import com.example.jwtcrud.user.middleware.UserDetailsImpl;
import com.example.jwtcrud.user.resource.AuthenticateUserResource;
import com.example.jwtcrud.user.resource.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String ENTITY = "User";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHandlerUser handler;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EnhancedModelMapper mapper;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticateRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = handler.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthenticateUserResource resource = mapper.map(userDetails, AuthenticateUserResource.class);
            resource.setRoles(roles);
            resource.setToken(token);

            AuthenticateUserResponse response = new AuthenticateUserResponse(resource);

            return ResponseEntity.ok(response.getResource());

        } catch (Exception e) {
            AuthenticateUserResponse response = new AuthenticateUserResponse(String.format("An error occurred while authenticating: %s", e.getMessage()));
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            AuthenticateUserResponse response = new AuthenticateUserResponse("Email is already used.");
            return ResponseEntity.badRequest()
                    .body(response.getMessage());
        }

        try {
            Set<String> rolesStringSet = request.getRoles();
            Set<Role> roles = new HashSet<>();

            if (rolesStringSet == null) {
                roleRepository.findByName(Roles.ROLE_ADMIN)
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            } else {
                rolesStringSet.forEach(roleString -> roleRepository.findByName(Roles.valueOf(roleString))
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            }

            logger.info("Roles: {}", roles);

            User user = new User()
                    .withFullName(request.getFullName())
                    .withPhone(request.getPhone())
                    .withEmail(request.getEmail())
                    .withPassword(encoder.encode(request.getPassword()))
                    .withRoles(roles);

            userRepository.save(user);
            UserResource resource = mapper.map(user, UserResource.class);
            RegisterUserResponse response = new RegisterUserResponse(resource);
            return ResponseEntity.ok(response.getResource());
        } catch (Exception e) {
            RegisterUserResponse response = new RegisterUserResponse(e.getMessage());
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, userId));
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);

        } catch (Exception e) {
            throw new ResourceValidationException(ENTITY, "An error occurred while saving user");
        }
    }

    @Override
    public User update(Long userId, User user) {
        try {
            return userRepository.findById(userId)
                    .map(u ->
                            userRepository.save(
                                    u.withFullName(user.getFullName())
                                            .withPhone(user.getPhone())
                                            .withEmail(user.getEmail())))
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY, userId));
        } catch (Exception e) {
            throw new ResourceValidationException(ENTITY, "An error occurred while updating user");
        }
    }

    @Override
    public ResponseEntity<?> delete(Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, userId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
        return UserDetailsImpl.build(user);
    }
}
