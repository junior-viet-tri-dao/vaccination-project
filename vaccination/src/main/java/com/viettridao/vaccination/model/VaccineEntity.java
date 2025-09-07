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
@Table(name = "vaccine")
// vắc xin
public class VaccineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "vaccine_id",length = 36)
    private String vaccineId;
    
    @Column(name = "vaccine_code", nullable = false)
    private String vaccineCode;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

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
    
    @Column(name = "unit", nullable = false)
    private String unit;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "vaccine_type_id")
    private VaccineTypeEntity vaccineType;
    
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

    @OneToMany(mappedBy = "vaccine")
    private List<VaccineBatchEntity> batches;
    
    @OneToMany(mappedBy = "vaccine")
    private List<VaccinationRegistrationDetailEntity> registrations;
}
