package com.example.jwtcrud.vendors.domain.service.communication;

import com.example.jwtcrud.shared.domain.service.communication.BaseResponse;
import com.example.jwtcrud.vendors.resource.AuthenticateVendorResource;

public class AuthenticateVendorResponse extends BaseResponse<AuthenticateVendorResource> {
    public AuthenticateVendorResponse(String message) {
        super(message);
    }

    public AuthenticateVendorResponse(AuthenticateVendorResource resource) {
        super(resource);
    }
}
