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
@Table(name = "bien_dong_kho")
public class BienDongKhoEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_bd", columnDefinition = "CHAR(36)")
	private String id;

	private Boolean isDeleted = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "ma_lo", nullable = false)
	private LoVacXinEntity loVacXin;

	@Enumerated(EnumType.STRING)
	@Column(name = "loai_bd", nullable = false)
	private LoaiBienDong loaiBD;

	@Column(name = "so_luong", nullable = false)
	private Integer soLuong;

	@Column(name = "ma_hoa_don")
	private String maHoaDon;

	@Enumerated(EnumType.STRING)
	@Column(name = "loai_hoa_don", length = 10)
	private LoaiHoaDon loaiHoaDon;

	@Column(name = "ghi_chu")
	private String ghiChu;

	@ManyToOne
	@JoinColumn(name = "thuc_hien_boi")
	private TaiKhoanEntity thucHienBoi;

	@Column(name = "ngay_thuc_hien")
	private LocalDateTime ngayThucHien;

	public enum LoaiBienDong {
		NHAP, XUAT, DIEU_CHINH
	}

	public enum LoaiHoaDon {
		NCC, KHACH, KHAC
	}
}
