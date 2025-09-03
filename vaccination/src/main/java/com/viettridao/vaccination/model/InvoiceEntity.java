package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "invoice")
public class InvoiceEntity {
    @Id
    @Column(name = "invoice_id", columnDefinition = "CHAR(36)")
    private String invoiceId;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @OneToOne(mappedBy = "invoice")
    private MedicalRecord medicalRecord;

    @OneToMany(mappedBy = "invoice")
    private List<VaccineBatch> vaccineBatches;
}
