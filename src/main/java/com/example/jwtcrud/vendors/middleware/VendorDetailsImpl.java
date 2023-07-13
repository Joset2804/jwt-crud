package com.example.jwtcrud.vendors.middleware;

import com.example.jwtcrud.vendors.domain.model.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class VendorDetailsImpl implements UserDetails {

    private Long id;
    private String fullName;
    private String phone;
    private Integer dni;
    private Date birthdayDate;
    private String email;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        VendorDetailsImpl user = (VendorDetailsImpl) other;
        return Objects.equals(id, user.id);
    }

    public static VendorDetailsImpl build(Vendor vendor) {
        List<GrantedAuthority> authorities = vendor.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new VendorDetailsImpl(
                vendor.getId(),
                vendor.getFullName(),
                vendor.getPhone(),
                vendor.getDni(),
                vendor.getBirthdayDate(),
                vendor.getEmail(),
                vendor.getPassword(),
                authorities
        );
    }

}
