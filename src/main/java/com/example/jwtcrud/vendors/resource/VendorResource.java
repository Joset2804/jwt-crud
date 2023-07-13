package com.example.jwtcrud.vendors.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class VendorResource {

    private Long id;

    private String fullName;

    private String phone;

    private Integer dni;

    private Date birthdayDate;

    private String email;

    private List<String> roles;
}
