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
@Table(name = "feedback")
// phản hồi
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feedback_id")
    private String feedbackId;

    @Column(name = "responsible_employee", nullable = false)
    private String responsibleEmployee;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(nullable = false, columnDefinition = "Text")
    private String content;

    @Column(name = "vaccination_time", nullable = false)
    private LocalDateTime vaccinationTime;

    @Column(name = "vaccination_location", nullable = false)
    private String vaccinationLocation;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "feedback_type_id")
    private FeedbackTypeEntity feedbackTypeEntity;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
}
