package com.viettridao.vaccination.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "vaccine_type")
public class VaccineType {
    @Id
    @Column(name = "vaccine_type_id", columnDefinition = "CHAR(36)")
    private String vaccineTypeId;

    @Column(name = "vaccine_type_name", nullable = false)
    private String vaccineTypeName;

    @OneToMany(mappedBy = "vaccineType")
    private List<Vaccine> vaccines;
}
