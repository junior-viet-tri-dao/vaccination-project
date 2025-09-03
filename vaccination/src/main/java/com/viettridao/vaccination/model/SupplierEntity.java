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
@Table(name = "supplier")
// nhà cung cấp
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "supplier")
    private List<VaccineBatchEntity> batches;
}
