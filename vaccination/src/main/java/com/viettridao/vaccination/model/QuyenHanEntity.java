package com.viettridao.vaccination.model;

import java.util.Set;

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
@Table(name = "quyen_han")
public class QuyenHanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_quyen_han", columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    private Boolean isDeleted = Boolean.FALSE;

    // Many-to-Many mappedBy
    @ManyToMany(mappedBy = "quyenHans")
    private Set<VaiTroEntity> vaiTros;
}
