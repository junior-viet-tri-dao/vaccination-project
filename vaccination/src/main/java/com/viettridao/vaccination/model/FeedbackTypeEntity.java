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
@Table(name = "feedback_type")
// loại phản hồi
public class FeedbackTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feedback_type_id")
    private String feedbackTypeId;

    @Column(name = "feedback_type_name", nullable = false)
    private String feedbackTypeName;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "feedbackTypeEntity")
    private List<FeedbackEntity> feedbackEntities;
}
