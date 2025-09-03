package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "account_role")
public class AccountRole {
    @EmbeddedId
    private AccountRoleId id;

    @ManyToOne @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @ManyToOne @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
