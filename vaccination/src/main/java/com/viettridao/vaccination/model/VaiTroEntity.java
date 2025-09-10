package com.viettridao.vaccination.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "vai_tro")
public class VaiTroEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_vai_tro", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "ten", nullable = false, unique = true)
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    @OneToMany(mappedBy = "vaiTro")
    private Set<TaiKhoanEntity> taiKhoans;
}
