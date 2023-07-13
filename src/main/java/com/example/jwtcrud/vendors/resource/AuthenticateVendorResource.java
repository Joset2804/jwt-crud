package com.example.jwtcrud.vendors.resource;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class AuthenticateVendorResource {

    private Long id;

    private String fullName;

    private String phone;

    private Integer dni;

    private Date birthdayDate;

    private String email;

    private List<String> roles;

    private String token;
}
