package com.example.jwtcrud.user.domain.model.entity;

import com.example.jwtcrud.security.domain.model.entity.Role;
import com.example.jwtcrud.shared.domain.model.AuditModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "users")
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotNull
    @NotBlank
    @Size(max=9)
    private String phone;

    @NotNull
    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min=8,max=200)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
