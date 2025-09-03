package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@Getter@Setter@NoArgsConstructor
@AllArgsConstructor @Builder
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "role_id", columnDefinition = "CHAR(36)")
    private String roleId;

    @Column(name = "role_name", nullable = false, length = 255)
    private String roleName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<AccountEntity> accounts = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<PermissionEntity> permissions = new HashSet<>();
}