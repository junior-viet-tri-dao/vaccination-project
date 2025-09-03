package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "consultation")
public class Consultation {
    @Id
    @Column(name = "consultation_id", columnDefinition = "CHAR(36)")
    private String consultationId;

    @Column(nullable = false)
    private String question;

    private String answer;

    @ManyToOne @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Column(name = "is_faq", nullable = false)
    private Boolean isFaq;
}
