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
@Table(name = "vaccine_batch")
// Lô vắc xin
public class VaccineBatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "batch_id", length = 36)
    private String batchId;

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

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

    @OneToMany(mappedBy = "batch")
    private List<VaccinationRegistrationDetailEntity> registrations;
}
