package com.viettridao.vaccination.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
class AccountRoleId implements java.io.Serializable {
    @Column(name = "account_id", columnDefinition = "CHAR(36)")
    private String accountId;

    @Column(name = "role_id", columnDefinition = "CHAR(36)")
    private String roleId;
}
