package com.example.jwtcrud.user.mapping;

import com.example.jwtcrud.security.domain.model.entity.Role;
import com.example.jwtcrud.shared.mapping.EnhancedModelMapper;
import com.example.jwtcrud.user.domain.model.entity.User;
import com.example.jwtcrud.user.resource.CreateUserResource;
import com.example.jwtcrud.user.resource.UpdateUserResource;
import com.example.jwtcrud.user.resource.UserResource;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class UserMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    Converter<Role, String> roleToString = new AbstractConverter<>() {
        @Override
        protected String convert(Role role) {
            return role == null ? null : role.getName().name();
        }
    };

    public UserResource toResource(User model) {
        mapper.addConverter(roleToString);
        return mapper.map(model, UserResource.class);
    }

    public List<UserResource> modelListToResource(List<User> modelList){
        mapper.addConverter(roleToString);
        return mapper.mapList(modelList, UserResource.class);
    }

    public User toModel(CreateUserResource resource) {
        return mapper.map(resource, User.class);
    }

    public User toModel(UpdateUserResource resource) {
        return mapper.map(resource, User.class);
    }
}
