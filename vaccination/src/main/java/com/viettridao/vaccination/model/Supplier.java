package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @Column(name = "supplier_id", columnDefinition = "CHAR(36)")
    private String supplierId;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @OneToMany(mappedBy = "supplier")
    private List<VaccineBatch> batches;
}
