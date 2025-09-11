package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
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
@Table(name = "dang_ky_tiem")
public class DangKyTiemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_dk", columnDefinition = "CHAR(36)")
    private String id;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "ma_lich", nullable = false)
    private LichTiemEntity lichTiem;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhanEntity benhNhan;

    @Column(name = "ngay_dk")
    private LocalDateTime ngayDK;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiDK trangThai = TrangThaiDK.CHO_DUYET;

    @ManyToOne
    @JoinColumn(name = "nguoi_dk")
    private TaiKhoanEntity nguoiDK;

    @Column(name = "ghi_chu")
    private String ghiChu;

    public enum TrangThaiDK { CHO_DUYET, DA_XAC_NHAN, HUY, DA_TIEU }
}
