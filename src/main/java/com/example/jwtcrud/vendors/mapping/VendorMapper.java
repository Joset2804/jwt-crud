package com.example.jwtcrud.vendors.mapping;

import com.example.jwtcrud.security.domain.model.entity.Role;
import com.example.jwtcrud.shared.mapping.EnhancedModelMapper;
import com.example.jwtcrud.vendors.domain.model.entity.Vendor;
import com.example.jwtcrud.vendors.resource.CreateVendorResource;
import com.example.jwtcrud.vendors.resource.UpdateVendorResource;
import com.example.jwtcrud.vendors.resource.VendorResource;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class VendorMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    Converter<Role, String> roleToString = new AbstractConverter<>() {
        @Override
        protected String convert(Role role) {
            return role == null ? null : role.getName().name();
        }
    };

    public VendorResource toResource(Vendor model) {
        mapper.addConverter(roleToString);
        return mapper.map(model, VendorResource.class);
    }

    public List<VendorResource> modelListToResource(List<Vendor> modelList){
        mapper.addConverter(roleToString);
        return mapper.mapList(modelList, VendorResource.class);
    }

    public Vendor toModel(CreateVendorResource resource) {
        return mapper.map(resource, Vendor.class);
    }

    public Vendor toModel(UpdateVendorResource resource) {
        return mapper.map(resource, Vendor.class);
    }



}
