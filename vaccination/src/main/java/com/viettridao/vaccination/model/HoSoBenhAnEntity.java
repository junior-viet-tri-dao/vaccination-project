package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
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
@Table(name = "ho_so_benh_an")
public class HoSoBenhAnEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_ho_so", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhanEntity benhNhan;

    @Column(name = "ngay_lap")
    private LocalDateTime ngayLap;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "bac_si_cap_nhat")
    private TaiKhoanEntity bacSiCapNhat;
}
