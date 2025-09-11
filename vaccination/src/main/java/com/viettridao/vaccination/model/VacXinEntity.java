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
@Table(name = "vac_xin")
public class VacXinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_vac_xin", columnDefinition = "CHAR(36)")
    private String id;

    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "ma_code", unique = true)
    private String maCode;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "loai")
    private String loai;

    @Column(name = "doi_tuong_tiem")
    private String doiTuongTiem;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @OneToMany(mappedBy = "vacXin")
    private Set<LichTiemEntity> lichTiems;

    @OneToMany(mappedBy = "vacXin")
    private Set<LoVacXinEntity> loVacXins;

    @OneToMany(mappedBy = "vacXin")
    private Set<ChiTietHDNCCEntity> chiTietHDNCCs;

    @OneToMany(mappedBy = "vacXin")
    private Set<ChiTietHDEntity> chiTietHDs;

    @OneToMany(mappedBy = "vacXin")
    private Set<DonThuocEntity> donThuocs;

    @OneToMany(mappedBy = "vacXin")
    private Set<BaoCaoPhanUngEntity> baoCaoPhanUngs;

    @OneToMany(mappedBy = "vacXin")
    private Set<BangGiaVacXinEntity> bangGiaVacXins;
}

