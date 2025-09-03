package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @Column(name = "employee_id", columnDefinition = "CHAR(36)")
    private String employeeId;

    @OneToOne @JoinColumn(name = "account_id", unique = true)
    private AccountEntity account;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    private Integer birthYear;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeParticipation> participations;

    @OneToMany(mappedBy = "employee")
    private List<Epidemic> epidemics;

    @OneToMany(mappedBy = "employee")
    private List<Consultation> consultations;
}
