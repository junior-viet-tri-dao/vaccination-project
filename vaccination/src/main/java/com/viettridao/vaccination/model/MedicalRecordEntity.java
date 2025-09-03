package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "medical_record")
// hồ sơ bệnh án
public class MedicalRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "medical_record_id")
    private String medicalRecordId;

    @Column(name = "post_vaccination_reaction", nullable = false)
    private String postVaccinationReaction;

    @Column(name = "effect_duration", nullable = false)
    private String effectDuration;

    @Column(name = "vaccination_time", nullable = false)
    private LocalDateTime vaccinationTime;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
            @JoinColumn(name = "batch_id", referencedColumnName = "batch_id"),
            @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    })
    private VaccinationRegistrationDetailEntity registrationDetail;

    @OneToOne
    @JoinColumn(name = "invoice_id", unique = true)
    private InvoiceEntity invoice;
}
