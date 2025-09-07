package com.viettridao.vaccination.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    private VaccineEntity vaccine;
}
