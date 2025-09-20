package com.viettridao.vaccination.model;

import java.time.LocalDate;
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

/**
 * Entity cho bảng DICH_BENH (tình hình dịch bệnh tại địa phương).
 *
 * - Mỗi dịch bệnh được tạo bởi 1 tài khoản (TaiKhoanEntity), 1 tài khoản có thể tạo nhiều bản ghi dịch bệnh.
 * - Các thuộc tính dựa trên sơ đồ và bảng mẫu bạn cung cấp.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dich_benh")
public class DichBenhEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_dich_benh", columnDefinition = "CHAR(36)")
    private String id;

    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "thoi_diem_khao_sat", nullable = false)
    private LocalDate thoiDiemKhaoSat;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "ten_benh", nullable = false)
    private String tenBenh;

    @Column(name = "so_nguoi_bi_nhiem", nullable = false)
    private Integer soNguoiBiNhiem;

    @Column(name = "duong_lay_nhiem")
    private String duongLayNhiem;

    @Column(name = "tac_hai_suc_khoe")
    private String tacHaiSucKhoe;

    @Column(name = "loai_vac_xin_phong_benh")
    private String loaiVacXinPhongBenh;

    @Column(name = "ghi_chu")
    private String ghiChu;

    // Quan hệ N-1 với TaiKhoanEntity (tài khoản tạo bản ghi)
    @ManyToOne
    @JoinColumn(name = "ma_tai_khoan", nullable = false)
    private TaiKhoanEntity taiKhoan;
}