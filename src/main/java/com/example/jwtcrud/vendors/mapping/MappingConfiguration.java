package com.example.jwtcrud.vendors.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("vendorMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public VendorMapper vendorMapper() { return new VendorMapper(); }
}
