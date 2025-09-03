package com.viettridao.vaccination.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class RolePermissionId implements java.io.Serializable {
    @Column(name = "role_id", columnDefinition = "CHAR(36)")
    private String roleId;

    @Column(name = "permission_id", columnDefinition = "CHAR(36)")
    private String permissionId;
}