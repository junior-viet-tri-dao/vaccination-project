package com.viettridao.vaccination.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_ho_so",columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhanEntity benhNhan;

    @Column(name = "ngay_lap")
    private LocalDateTime ngayLap;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "bac_si_cap_nhat")
    private TaiKhoanEntity bacSiCapNhat;
}
