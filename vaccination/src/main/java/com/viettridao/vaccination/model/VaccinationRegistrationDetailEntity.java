package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vaccination_registration_detail")
// chi tiết đăng ký tiêm
public class VaccinationRegistrationDetailEntity {
    @EmbeddedId
    private VaccinationRegistrationKey id;

    @Column(name = "scheduled_time", nullable = false)
    private Date scheduledTime;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne(mappedBy = "registrationDetail")
    private MedicalRecordEntity medicalRecordEntity;

    @ManyToOne
    @JoinColumn(name = "patient_id", columnDefinition = "varchar(36)")
    @MapsId("patientId")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "schedule_id", columnDefinition = "varchar(36)")
    @MapsId("scheduleId")
    private VaccinationScheduleEntity schedule;
    
    @ManyToOne
    @JoinColumn(name = "vaccine_id", columnDefinition = "varchar(36)")
    @MapsId("vaccineId")
    private VaccineEntity vaccine;
}
