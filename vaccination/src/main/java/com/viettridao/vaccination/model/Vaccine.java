package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "vaccine")
public class Vaccine {
    @Id
    @Column(name = "vaccine_id", columnDefinition = "CHAR(36)")
    private String vaccineId;

    @ManyToOne @JoinColumn(name = "vaccine_type_id")
    private VaccineType vaccineType;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    private String dosage;

    @Column(name = "prevent_disease", nullable = false)
    private String preventDisease;

    @Column(name = "age_group", nullable = false)
    private String ageGroup;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "storage_condition", nullable = false)
    private String storageCondition;

    @OneToMany(mappedBy = "vaccine")
    private List<VaccineBatch> batches;
}
