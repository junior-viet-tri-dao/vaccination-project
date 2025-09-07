package com.viettridao.vaccination.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VaccinationRegistrationKey {
    @Column(length = 36)
    private String scheduleId;

    @Column(length = 36)
    private String patientId;

    @Column(length = 36)
    private String vaccineId;
}
