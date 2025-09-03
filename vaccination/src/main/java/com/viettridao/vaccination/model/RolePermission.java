package com.viettridao.vaccination.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role_permission")
public class RolePermission {
    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;
}
