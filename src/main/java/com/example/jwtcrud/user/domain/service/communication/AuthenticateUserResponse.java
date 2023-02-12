package com.example.jwtcrud.user.domain.service.communication;

import com.example.jwtcrud.shared.domain.service.communication.BaseResponse;
import com.example.jwtcrud.user.resource.AuthenticateUserResource;

public class AuthenticateUserResponse extends BaseResponse<AuthenticateUserResource> {
    public AuthenticateUserResponse(String message) {
        super(message);
    }

    public AuthenticateUserResponse(AuthenticateUserResource resource) {
        super(resource);
    }
}
