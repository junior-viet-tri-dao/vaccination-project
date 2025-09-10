package com.viettridao.vaccination.model;

import java.time.LocalDate;
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
@Table(name = "lo_vac_xin")
public class LoVacXinEntity {

    @Id
    @GeneratedValue
    @Column(name = "ma_lo", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ma_vac_xin", nullable = false)
    private VacXinEntity vacXin;

    @Column(name = "malo_code", nullable = false)
    private String maLoCode;

    @Column(name = "nuoc_san_xuat")
    private String nuocSanXuat;

    @Column(name = "ngay_san_xuat")
    private LocalDate ngaySanXuat;
    
    @Column(name = "ham_luong")
    private String hamLuong;
    
    @Column(name = "don_vi")
    private String donVi;

    @Column(name = "tinh_trang")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TinhTrang tinhTrang = TinhTrang.CO;


    @Column(name = "han_su_dung")
    private LocalDate hanSuDung;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private Integer donGia;

    @Column(name = "dieu_kien_bao_quan")
    private String dieuKienBaoQuan;

    @Column(name = "so_giay_phep")
    private String soGiayPhep;

    @Column(name = "ngay_nhap")
    private LocalDate ngayNhap;

    @ManyToOne
    @JoinColumn(name = "ma_ncc")
    private NhaCungCapEntity nhaCungCap;

    @OneToMany(mappedBy = "loVacXin")
    private Set<ChiTietHDNCCEntity> chiTietHDNCCs;

    @OneToMany(mappedBy = "loVacXin")
    private Set<ChiTietHDEntity> chiTietHDs;

    @OneToMany(mappedBy = "loVacXin")
    private Set<BienDongKhoEntity> bienDongKhos;
    
    public enum TinhTrang {
        CO, HET
    }
}
