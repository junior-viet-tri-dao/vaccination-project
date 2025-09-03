package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "feedback_type")
public class FeedbackType {
    @Id
    @Column(name = "feedback_type_id", columnDefinition = "CHAR(36)")
    private String feedbackTypeId;

    @Column(name = "feedback_type_name", nullable = false)
    private String feedbackTypeName;

    @OneToMany(mappedBy = "feedbackType")
    private List<Feedback> feedbacks;
}
