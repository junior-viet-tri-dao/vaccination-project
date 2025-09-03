package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @Column(name = "feedback_id", columnDefinition = "CHAR(36)")
    private String feedbackId;

    @ManyToOne @JoinColumn(name = "feedback_type_id")
    private FeedbackType feedbackType;

    @ManyToOne @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @Column(name = "responsible_employee", nullable = false)
    private String responsibleEmployee;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(nullable = false)
    private String content;

    @Column(name = "vaccination_time", nullable = false)
    private Date vaccinationTime;

    @Column(name = "vaccination_location", nullable = false)
    private String vaccinationLocation;
}
