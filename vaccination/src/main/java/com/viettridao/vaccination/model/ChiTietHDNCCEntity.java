package com.viettridao.vaccination.model;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "chi_tiet_hd_ncc")
public class ChiTietHDNCCEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_ct", columnDefinition = "BINARY(16)")
    private UUID id;

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

