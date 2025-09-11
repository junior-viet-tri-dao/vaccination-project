package com.viettridao.vaccination.model;

import java.util.Set;
import java.util.UUID;

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
@Table(name = "vai_tro")
public class VaiTroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_vai_tro", columnDefinition = "CHAR(36)")
    private String id;

    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "ten", nullable = false, unique = true)
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    @OneToMany(mappedBy = "vaiTro")
    private Set<TaiKhoanEntity> taiKhoans;
}
