package com.example.jwtcrud.vendors.service;

import com.example.jwtcrud.security.domain.model.entity.Role;
import com.example.jwtcrud.security.domain.model.enumeration.Roles;
import com.example.jwtcrud.security.domain.persistence.RoleRepository;
import com.example.jwtcrud.security.domain.service.communication.AuthenticateRequest;
import com.example.jwtcrud.shared.exception.ResourceNotFoundException;
import com.example.jwtcrud.shared.exception.ResourceValidationException;
import com.example.jwtcrud.shared.mapping.EnhancedModelMapper;
import com.example.jwtcrud.vendors.domain.model.entity.Vendor;
import com.example.jwtcrud.vendors.domain.persistence.VendorRepository;
import com.example.jwtcrud.vendors.domain.service.VendorService;
import com.example.jwtcrud.vendors.domain.service.communication.AuthenticateVendorResponse;
import com.example.jwtcrud.vendors.domain.service.communication.RegisterVendorRequest;
import com.example.jwtcrud.vendors.domain.service.communication.RegisterVendorResponse;
import com.example.jwtcrud.vendors.middleware.JwtHandlerVendor;
import com.example.jwtcrud.vendors.middleware.VendorDetailsImpl;
import com.example.jwtcrud.vendors.resource.AuthenticateVendorResource;
import com.example.jwtcrud.vendors.resource.VendorResource;
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
public class VendorServiceImpl implements VendorService {

    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    private static final String ENTITY = "Vendor";

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHandlerVendor handler;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EnhancedModelMapper mapper;

    @Override
    public List<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticateRequest request) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = handler.generateToken(authentication);

            VendorDetailsImpl vendorDetails = (VendorDetailsImpl) authentication.getPrincipal();

            List<String> roles = vendorDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthenticateVendorResource resource = mapper.map(vendorDetails, AuthenticateVendorResource.class);

            resource.setRoles(roles);
            resource.setToken(token);

            AuthenticateVendorResponse response = new AuthenticateVendorResponse(resource);

            return ResponseEntity.ok(response.getResource());


        } catch (Exception e) {

            AuthenticateVendorResponse response = new AuthenticateVendorResponse(String.format("An error occurred while authenticating: %s", e.getMessage()));

            return ResponseEntity.badRequest().body(response.getMessage());

        }
    }

    @Override
    public ResponseEntity<?> register(RegisterVendorRequest request) {

        if (vendorRepository.existsByEmail(request.getEmail())) {

            AuthenticateVendorResponse response = new AuthenticateVendorResponse("Email is already in use!");

            return ResponseEntity.badRequest().body(response.getMessage());

        }

        try {

            Set<String> rolesStringSet = request.getRoles();

            Set<Role> roles = new HashSet<>();

            if (rolesStringSet == null) {

                roleRepository.findByName(Roles.ROLE_VENDOR)
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            } else {

               rolesStringSet.forEach(roleString -> roleRepository.findByName(Roles.valueOf(roleString))
                       .map(roles::add)
                       .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));

            }

            logger.info("Roles: {}", roles);

            Vendor vendor = new Vendor()
                    .withFullName(request.getFullName())
                    .withPhone(request.getPhone())
                    .withDni(request.getDni())
                    .withBirthdayDate(request.getBirthdayDate())
                    .withEmail(request.getEmail())
                    .withPassword(encoder.encode(request.getPassword()))
                    .withRoles(roles);

            vendorRepository.save(vendor);

            VendorResource resource = mapper.map(vendor, VendorResource.class);

            RegisterVendorResponse response = new RegisterVendorResponse(resource);

            return ResponseEntity.ok(response.getResource());
        } catch (Exception e) {

            RegisterVendorResponse response = new RegisterVendorResponse(e.getMessage());

            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @Override
    public Vendor getById(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, vendorId));
    }

    @Override
    public Vendor update(Long vendorId, Vendor vendor) {
        try {
            return vendorRepository.findById(vendorId)
                    .map(v -> vendorRepository.save(
                            v.withFullName(vendor.getFullName())
                                    .withPhone(vendor.getPhone())
                                    .withDni(vendor.getDni())
                                    .withBirthdayDate(vendor.getBirthdayDate())
                                    .withEmail(vendor.getEmail())))
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY, vendorId));
        } catch (Exception e) {
            throw new ResourceValidationException(ENTITY, "An error occurred while updating the vendor: ");
        }
    }

    @Override
    public ResponseEntity<?> delete(Long vendorId) {
        return vendorRepository.findById(vendorId).map(vendor -> {
            vendorRepository.delete(vendor);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, vendorId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Vendor vendor = vendorRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
        return VendorDetailsImpl.build(vendor);
    }
}
