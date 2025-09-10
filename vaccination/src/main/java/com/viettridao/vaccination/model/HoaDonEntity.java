package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "hoa_don")
public class HoaDonEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_hd", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "so_hoa_don", nullable = false, unique = true)
    private String soHoaDon;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private BenhNhanEntity benhNhan;

    @ManyToOne
    @JoinColumn(name = "tao_boi")
    private TaiKhoanEntity taoBoi;

    @Column(name = "ngay_hd")
    private LocalDateTime ngayHD;

    @Column(name = "tong_tien")
    private Double tongTien;

    @Column(name = "da_xoa")
    private Boolean daXoa = false;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @OneToMany(mappedBy = "hoaDon")
    private Set<ChiTietHDEntity> chiTietHDs;
}
