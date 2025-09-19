package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "tai_khoan")
public class TaiKhoanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_tai_khoan", columnDefinition = "CHAR(36)")
	private String id;

	@Column(name = "ten_dang_nhap", nullable = false, unique = true)
	private String tenDangNhap;

	@Column(name = "mat_khau_hash", nullable = false)
	private String matKhauHash;

	@Column(name = "ho_ten")
	private String hoTen;

	@Column(name = "so_cmnd")
	private String soCmnd;

	@Column(name = "so_dien_thoai")
	private String soDienThoai;

	@Column(name = "email")
	private String email;

	@Column(name = "dia_chi")
	private String diaChi;
	
	private Boolean isDeleted = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "ma_vai_tro", nullable = false)
	private VaiTroEntity vaiTro;

	@Column(name = "hoat_dong")
	private Boolean hoatDong = true;

	@Column(name = "ngay_tao")
	private LocalDateTime ngayTao;

	@Column(name = "ngay_cap_nhat")
	private LocalDateTime ngayCapNhat;

	@OneToMany(mappedBy = "taoBoiTaiKhoan")
	private Set<BenhNhanEntity> benhNhans;

	@OneToMany(mappedBy = "taoBoi")
	private Set<LichTiemEntity> lichTiems;

	@OneToMany(mappedBy = "bacSiCapNhat")
	private Set<HoSoBenhAnEntity> hoSoBenhAns;

	@OneToMany(mappedBy = "taoBoi")
	private Set<HoaDonNCCEntity> hoaDonNCCs;

	@OneToMany(mappedBy = "taoBoi")
	private Set<HoaDonEntity> hoaDons;

	@OneToMany(mappedBy = "thucHienBoi")
	private Set<BienDongKhoEntity> bienDongKhos;

	@OneToMany(mappedBy = "nguoiDK")
	private Set<DangKyTiemEntity> dangKyTiems;

	@OneToMany(mappedBy = "keBoi")
	private Set<DonThuocEntity> donThuocs;

	@OneToMany(mappedBy = "taoBoi")
	private Set<BaoCaoPhanUngEntity> baoCaoPhanUngs;

	@OneToMany(mappedBy = "taoBoi")
	private Set<PhanHoiEntity> phanHois;

	@OneToMany(mappedBy = "taoBoi")
	private Set<BangGiaVacXinEntity> bangGiaVacXins;
	
	@OneToMany(mappedBy = "nguoiThucHien")
	private List<KetQuaTiemEntity> ketQuaTiemsThucHien;
	
	

	@OneToMany(mappedBy = "taiKhoan")
	private Set<DichBenhEntity> dichBenhs;

}
