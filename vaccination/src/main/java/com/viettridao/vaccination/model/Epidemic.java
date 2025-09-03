package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "epidemic")
public class Epidemic {
    @Id
    @Column(name = "epidemic_id", columnDefinition = "CHAR(36)")
    private String epidemicId;

    @ManyToOne @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Column(name = "epidemic_name", nullable = false)
    private String epidemicName;

    @Column(name = "transmission_mode", nullable = false)
    private String transmissionMode;

    @Column(name = "health_impact", nullable = false)
    private String healthImpact;

    @Column(name = "infected_count", nullable = false)
    private Integer infectedCount;

    @Column(nullable = false)
    private String address;

    private String note;

    @Column(name = "survey_time", nullable = false)
    private Date surveyTime;
}
