package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patient")
// bệnh nhân
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "patient_id", length = 36)
    private String patientId;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "patient_code", nullable = false, unique = true)
    private String patientCode;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    private String address;

    @Column(name = "guardian_name", nullable = false)
    private String guardianName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, length = 50)
    private String gender;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private AccountEntity account;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "patient")
    private List<FeedbackEntity> feedbackEntities;

    @OneToMany(mappedBy = "patient")
    private List<ConsultationEntity> consultationEntities;

    @OneToMany(mappedBy = "patient")
    private List<VaccinationRegistrationDetailEntity> registrations;
}

