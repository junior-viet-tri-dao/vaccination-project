package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @Column(name = "medical_record_id", columnDefinition = "CHAR(36)")
    private String medicalRecordId;

    @OneToOne @JoinColumn(name = "registration_detail_id", unique = true)
    private VaccinationRegistrationDetail registrationDetail;

    @OneToOne @JoinColumn(name = "invoice_id", unique = true)
    private InvoiceEntity invoice;

    @Column(name = "post_vaccination_reaction", nullable = false)
    private String postVaccinationReaction;

    @Column(name = "effect_duration", nullable = false)
    private String effectDuration;

    @Column(name = "vaccination_time", nullable = false)
    private Date vaccinationTime;

    private String result;

    private String note;
}
