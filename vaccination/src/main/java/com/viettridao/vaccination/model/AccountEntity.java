package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity implements Serializable, UserDetails {
    @Id
    @Column(name = "account_id", columnDefinition = "CHAR(36)")
    private String accountId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "identity_number", unique = true, nullable = false)
    private String identityNumber;

    @Column(nullable = false)
    private String address;

    private String description;

    @Column(nullable = false)
    private String email;

    // ✅ Một Account chỉ có một Role
    @ManyToOne
    @JoinColumn(name = "role_id") // cột FK nằm trong bảng account
    private RoleEntity role;

    @OneToOne(mappedBy = "account")
    private PatientEntity patient;

    @OneToOne(mappedBy = "account")
    private EmployeeEntity employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
