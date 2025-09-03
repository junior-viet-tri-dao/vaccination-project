package com.viettridao.vaccination.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class EmployeeParticipationId implements java.io.Serializable {
    @Column(name = "employee_id", columnDefinition = "CHAR(36)")
    private String employeeId;

    @Column(name = "schedule_id", columnDefinition = "CHAR(36)")
    private String scheduleId;
}
