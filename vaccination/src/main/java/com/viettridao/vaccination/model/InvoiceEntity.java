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
@Table(name = "invoice")
// hóa đơn
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "invoice_id")
    private String invoiceId;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne(mappedBy = "invoice")
    private MedicalRecordEntity medicalRecordEntity;

    @OneToOne(mappedBy = "invoice")
    private VaccineBatchEntity vaccineBatch;
}
