package com.viettridao.vaccination.model;

import jakarta.persistence.*;
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
    private Integer totalAmount;

    @Column(name = "customer_price")
    private Integer customerPrice;

    @Column(name = "supplier_price")
    private Integer supplierPrice;

    @Column(name = "quantity_invoice", nullable = false)
    private Integer quantityInvoice;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToOne(mappedBy = "invoice")
    private MedicalRecordEntity medicalRecordEntity;

    @OneToOne(mappedBy = "invoice")
    private VaccineEntity vaccine;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;
}