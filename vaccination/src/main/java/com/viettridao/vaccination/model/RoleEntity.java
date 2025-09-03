package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @Column(name = "role_id", columnDefinition = "CHAR(36)")
    private String roleId;

    @Column(name = "role_name", nullable = false, length = 255)
    private String roleName;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Một Role có nhiều Permission
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PermissionEntity> permissions = new HashSet<>();

    // Một Role có nhiều Account
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountEntity> accounts = new HashSet<>();
}
