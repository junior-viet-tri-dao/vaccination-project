package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ket_qua_tiem")
public class KetQuaTiemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_kq", columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhanEntity benhNhan;

    @ManyToOne
    @JoinColumn(name = "ma_lich", nullable = false)
    private LichTiemEntity lichTiem;

    @Column(name = "ngay_tiem", nullable = false)
    private LocalDateTime ngayTiem;

    @ManyToOne
    @JoinColumn(name = "nguoi_thuc_hien")
    private TaiKhoanEntity nguoiThucHien;

    @Enumerated(EnumType.STRING)
    @Column(name = "tinh_trang")
    private TinhTrangTinhTrang tinhTrang = TinhTrangTinhTrang.CHUA_TIEM;

    @Column(name = "phan_ung_sau_tiem")
    private String phanUngSauTiem;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "isDeleted")
    private Boolean isDeleted = Boolean.FALSE;

    public enum TinhTrangTinhTrang {
        HOAN_THANH, HUY, CHUA_TIEM
    }
}