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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "account")
public class AccountEntity {
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

    @ManyToMany
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToOne(mappedBy = "account")
    private PatientEntity patient;

    @OneToOne(mappedBy = "account")
    private EmployeeEntity employee;
}
