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
@Table(name = "vaccine_type")
// Loại vắc xin
public class VaccineTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "vaccine_type_id")
    private String vaccineTypeId;

    @Column(name = "vaccine_type_name", nullable = false)
    private String vaccineTypeName;

    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "vaccineType")
    private List<VaccineEntity> vaccineEntities;
}
