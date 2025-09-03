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
    @Column(length = 30)
    private String scheduleId;

    @Column(length = 30)
    private String patientId;

    @Column(length = 30)
    private String batchId;
}
