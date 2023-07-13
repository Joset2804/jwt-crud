package com.example.jwtcrud.vendors.domain.service;

import com.example.jwtcrud.security.domain.service.communication.AuthenticateRequest;
import com.example.jwtcrud.vendors.domain.model.entity.Vendor;
import com.example.jwtcrud.vendors.domain.service.communication.RegisterVendorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface VendorService extends UserDetailsService {
    List<Vendor> getAll();
    ResponseEntity<?> authenticate(AuthenticateRequest request);
    ResponseEntity<?> register(RegisterVendorRequest vendor);

    Vendor getById(Long vendorId);

    Vendor update(Long vendorId, Vendor vendor);
    ResponseEntity<?> delete(Long vendorId);
}
