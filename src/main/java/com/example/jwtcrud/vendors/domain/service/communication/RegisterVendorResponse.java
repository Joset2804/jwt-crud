package com.example.jwtcrud.vendors.domain.service.communication;

import com.example.jwtcrud.shared.domain.service.communication.BaseResponse;
import com.example.jwtcrud.vendors.resource.VendorResource;

public class RegisterVendorResponse extends BaseResponse<VendorResource> {

    public RegisterVendorResponse(String message) {
        super(message);
    }

    public RegisterVendorResponse(VendorResource resource) {
        super(resource);
    }
}
