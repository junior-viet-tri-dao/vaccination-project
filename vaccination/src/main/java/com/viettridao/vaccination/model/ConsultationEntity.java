package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "consultation")
//lượt tư vấn
public class ConsultationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "consultation_id")
    private String consultationId;

    @Column(nullable = false)
    private String question;

    private String answer;

    @Column(name = "is_faq", nullable = false)
    private Boolean isFaq;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
