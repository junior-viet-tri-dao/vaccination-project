package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "vaccination_schedule")
public class VaccinationSchedule {
    @Id
    @Column(name = "schedule_id", columnDefinition = "CHAR(36)")
    private String scheduleId;

    @Column(name = "target_group", nullable = false)
    private String targetGroup;

    @Column(name = "general_time", nullable = false)
    private String generalTime;

    private String note;

    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;

    @Column(name = "vaccination_date", nullable = false)
    private Date vaccinationDate;

    @Column(nullable = false)
    private String location;

    @ManyToOne @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @OneToMany(mappedBy = "schedule")
    private List<VaccinationRegistrationDetail> registrations;

    @OneToMany(mappedBy = "schedule")
    private List<EmployeeParticipation> participations;
}
