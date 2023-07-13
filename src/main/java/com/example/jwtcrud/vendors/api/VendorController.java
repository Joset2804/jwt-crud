package com.example.jwtcrud.vendors.api;

import com.example.jwtcrud.security.domain.service.communication.AuthenticateRequest;
import com.example.jwtcrud.vendors.domain.service.VendorService;
import com.example.jwtcrud.vendors.domain.service.communication.RegisterVendorRequest;
import com.example.jwtcrud.vendors.mapping.VendorMapper;
import com.example.jwtcrud.vendors.resource.UpdateVendorResource;
import com.example.jwtcrud.vendors.resource.VendorResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vendors")
@CrossOrigin
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorMapper vendorMapper;

    @GetMapping
    public List<VendorResource> getAllVendors() {
        return vendorMapper.modelListToResource(vendorService.getAll());
    }

    @GetMapping("{vendorId}")
    @PreAuthorize("hasRole('VENDOR')")
    public VendorResource getVendorById(@PathVariable("vendorId") Long vendorId) {
        return vendorMapper.toResource(vendorService.getById(vendorId));
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> authenticateVendor(@Valid @RequestBody AuthenticateRequest request) {
        return vendorService.authenticate(request);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> registerVendor(@Valid @RequestBody RegisterVendorRequest request) {
        return vendorService.register(request);
    }

    @PutMapping("{vendorId}")
    @PreAuthorize("hasRole('VENDOR')")
    public VendorResource updateVendor(@PathVariable Long vendorId, @RequestBody UpdateVendorResource request) {
        return vendorMapper.toResource(vendorService.update(vendorId, vendorMapper.toModel(request)));
    }

    @DeleteMapping("{vendorId}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteVendor(@PathVariable Long vendorId) {
        return vendorService.delete(vendorId);
    }
}
