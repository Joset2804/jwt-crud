package com.example.jwtcrud.security.api;

import com.example.jwtcrud.security.domain.service.RoleService;
import com.example.jwtcrud.security.mapping.RoleMapper;
import com.example.jwtcrud.security.resource.RoleResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin
public class RolesController {

    private final RoleService roleService;
    private final RoleMapper mapper;

    public RolesController(RoleService roleService, RoleMapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getAllRoles() {
//        return ResponseEntity.ok(mapper.modelListToResource(roleService.getAll()));
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRoles(Pageable pageable) {
        Page<RoleResource> resources = mapper.modelListToPage(roleService.getAll(), pageable);
        return ResponseEntity.ok(resources);
    }

}
