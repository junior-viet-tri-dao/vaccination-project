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
@Table(name = "vaccination_schedule")
// lịch tiêm chủng
public class VaccinationScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_id", length = 30)
    private String scheduleId;

    @Column(name = "target_group", nullable = false)
    private String targetGroup;

    @Column(name = "general_time", nullable = false)
    private String generalTime;

    @Column(columnDefinition = "Text")
    private String note;

    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;

    @Column(name = "vaccination_date", nullable = false)
    private LocalDate vaccinationDate;

    @Column(nullable = false)
    private String location;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "schedule")
    private List<VaccinationRegistrationDetailEntity> registrations;

    @ManyToMany(mappedBy = "scheduleEntities")
    private Set<EmployeeEntity> employees;
}
