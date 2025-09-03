package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// quyền hạn
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "permission_id")
    private String permissionId;

    @Column(name = "permission_name", nullable = false)
    private String permissionName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "permissions")
    private Set<RoleEntity> roles;
}
