package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "vaccination_registration_detail")
public class VaccinationRegistrationDetail {
    @Id
    @Column(name = "registration_detail_id", columnDefinition = "CHAR(36)")
    private String registrationDetailId;

    @ManyToOne @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne @JoinColumn(name = "batch_id")
    private VaccineBatch batch;

    @ManyToOne @JoinColumn(name = "schedule_id")
    private VaccinationSchedule schedule;

    @Column(name = "scheduled_time", nullable = false)
    private Date scheduledTime;

    @OneToOne(mappedBy = "registrationDetail")
    private MedicalRecord medicalRecord;
}
