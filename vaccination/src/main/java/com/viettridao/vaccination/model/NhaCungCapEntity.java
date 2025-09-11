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
@Table(name = "nha_cung_cap")
public class NhaCungCapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_ncc", columnDefinition = "CHAR(36)")
    private String id;

    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "nguoi_lien_he")
    private String nguoiLienHe;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @OneToMany(mappedBy = "nhaCungCap")
    private Set<LoVacXinEntity> loVacXins;

    @OneToMany(mappedBy = "nhaCungCap")
    private Set<HoaDonNCCEntity> hoaDonNCCs;
}
