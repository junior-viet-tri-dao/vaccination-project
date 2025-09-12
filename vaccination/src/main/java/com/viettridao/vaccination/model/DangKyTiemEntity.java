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
@Table(name = "dang_ky_tiem")
public class DangKyTiemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_dk", columnDefinition = "CHAR(36)")
	private String id;

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

	private Boolean isDeleted = Boolean.FALSE;

	public enum TrangThaiDK {
		CHO_DUYET, DA_XAC_NHAN, HUY, DA_TIEU
	}
}
