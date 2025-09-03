package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
    @Column(name = "patient_id", columnDefinition = "CHAR(36)")
    private String patientId;

    @OneToOne @JoinColumn(name = "account_id", unique = true)
    private AccountEntity account;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    private String address;

    @Column(name = "guardian_name", nullable = false)
    private String guardianName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, length = 50)
    private String gender;

    @OneToMany(mappedBy = "patient")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "patient")
    private List<VaccinationRegistrationDetail> registrations;
}

