package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
// nhân viên
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String phone;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private AccountEntity account;

    @OneToMany(mappedBy = "employee")
    private List<EpidemicEntity> epidemicEntities;

    @OneToMany(mappedBy = "employee")
    private List<ConsultationEntity> consultationEntities;

    @ManyToMany
    @JoinTable(
            name = "employee_vaccination_schedule",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private Set<VaccinationScheduleEntity> scheduleEntities;
}
