package com.viettridao.vaccination.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
@Table(name = "benh_nhan")
public class BenhNhanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_benh_nhan", columnDefinition = "CHAR(36)")
	private String id;

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

	private Boolean isDeleted = Boolean.FALSE;

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

	@OneToMany(mappedBy = "benhNhan")
	private List<KetQuaTiemEntity> ketQuaTiems;

	@OneToOne
	@JoinColumn(name = "ma_tai_khoan", referencedColumnName = "ma_tai_khoan", unique = true)
	private TaiKhoanEntity taiKhoan;

	public enum GioiTinh {
		NAM, NU
	}
}