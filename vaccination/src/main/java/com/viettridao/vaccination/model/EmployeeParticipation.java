package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "employee_participation")
public class EmployeeParticipation {
    @EmbeddedId
    private EmployeeParticipationId id;

    @ManyToOne @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    private VaccinationSchedule schedule;
}
