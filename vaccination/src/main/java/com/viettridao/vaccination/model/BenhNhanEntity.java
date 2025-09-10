package com.viettridao.vaccination.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "benh_nhan")
public class BenhNhanEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_benh_nhan", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh")
    @Builder.Default
    private GioiTinh gioiTinh = GioiTinh.NAM;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "ten_nguoi_giam_ho")
    private String tenNguoiGiamHo;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "tao_boi_tai_khoan")
    private TaiKhoanEntity taoBoiTaiKhoan;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @OneToMany(mappedBy = "benhNhan")
    private Set<HoSoBenhAnEntity> hoSoBenhAns;

    @OneToMany(mappedBy = "benhNhan")
    private Set<DangKyTiemEntity> dangKyTiems;

    @OneToMany(mappedBy = "benhNhan")
    private Set<HoaDonEntity> hoaDons;

    @OneToMany(mappedBy = "benhNhan")
    private Set<DonThuocEntity> donThuocs;

    @OneToMany(mappedBy = "benhNhan")
    private Set<BaoCaoPhanUngEntity> baoCaoPhanUngs;

    @OneToMany(mappedBy = "benhNhan")
    private Set<PhanHoiEntity> phanHois;

    public enum GioiTinh {
        NAM, NU
    }
}
