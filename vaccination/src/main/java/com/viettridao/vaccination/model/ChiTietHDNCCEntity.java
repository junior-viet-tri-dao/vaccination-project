package com.viettridao.vaccination.model;


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
@Table(name = "chi_tiet_hd_ncc")
public class ChiTietHDNCCEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_ct", columnDefinition = "CHAR(36)")
    private String id;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "ma_hd_ncc", nullable = false)
    private HoaDonNCCEntity hoaDonNCC;

    @ManyToOne
    @JoinColumn(name = "ma_vac_xin", nullable = false)
    private VacXinEntity vacXin;

    @Column(name = "so_lo")
    private String soLo;

    @ManyToOne
    @JoinColumn(name = "ma_lo")
    private LoVacXinEntity loVacXin;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private Double donGia;
}

