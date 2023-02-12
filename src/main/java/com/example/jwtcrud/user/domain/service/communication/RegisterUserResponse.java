package com.example.jwtcrud.user.domain.service.communication;

import com.example.jwtcrud.shared.domain.service.communication.BaseResponse;
import com.example.jwtcrud.user.resource.UserResource;

public class RegisterUserResponse extends BaseResponse<UserResource> {
    public RegisterUserResponse(String message) {
        super(message);
    }

    public RegisterUserResponse(UserResource resource) {
        super(resource);
    }
}
