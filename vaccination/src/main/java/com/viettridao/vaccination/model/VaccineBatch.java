package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "vaccine_batch")
public class VaccineBatch {
    @Id
    @Column(name = "batch_id", columnDefinition = "CHAR(36)")
    private String batchId;

    @ManyToOne @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @ManyToOne @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String status;

    @Column(name = "received_date", nullable = false)
    private Date receivedDate;

    @Column(name = "country_of_origin", nullable = false)
    private String countryOfOrigin;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    private String note;

    @Column(name = "manufacture_year", length = 10)
    private String manufactureYear;

    @OneToMany(mappedBy = "batch")
    private List<VaccinationRegistrationDetail> registrations;
}
