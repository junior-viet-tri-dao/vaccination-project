package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "epidemic")
// dịch bệnh
public class EpidemicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "epidemic_id")
    private String epidemicId;

    @Column(name = "epidemic_name", nullable = false)
    private String epidemicName;

    @Column(name = "transmission_mode", nullable = false)
    private String transmissionMode;

    @Column(name = "health_impact", nullable = false, columnDefinition = "TEXT")
    private String healthImpact;

    @Column(name = "infected_count", nullable = false)
    private Integer infectedCount;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String note;

    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "survey_time", nullable = false)
    private LocalDate surveyTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
