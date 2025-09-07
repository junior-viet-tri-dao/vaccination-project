package com.viettridao.vaccination.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vaccine_batch")
// Lô vắc xin
public class VaccineBatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "batch_id", length = 36)
    private String batchId;
    
    @Column(name = "batch_code",nullable = false)
    private String batchCode;
    
    @Column(name = "production_year",nullable = false)
    private String productionYear;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String status;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @Column(name = "country_of_origin", nullable = false)
    private String countryOfOrigin;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private VaccineEntity vaccine;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    private Boolean isDeleted = Boolean.FALSE;

}
