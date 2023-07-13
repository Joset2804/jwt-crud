package com.example.jwtcrud.vendors.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class CreateVendorResource {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotNull
    @NotBlank
    @Size(max=9)
    private String phone;

    @NotNull
    private Integer dni;

    @NotNull
    private Date birthdayDate;

    @NotNull
    @NotBlank
    @Size(max=50)
    @Email
    private String email;
}
