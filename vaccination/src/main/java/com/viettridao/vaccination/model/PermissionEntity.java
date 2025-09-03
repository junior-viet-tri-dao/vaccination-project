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
public class PermissionEntity {
    @Id
    @Column(name = "permission_id", columnDefinition = "CHAR(36)")
    private String permissionId;

    @Column(name = "permission_name", nullable = false, length = 255)
    private String permissionName;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ✅ Nhiều permission -> 1 role
    @ManyToOne
    @JoinColumn(name = "role_id") // FK nằm trong bảng permission
    private RoleEntity role;
}
