package com.viettridao.vaccination.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "phan_hoi")
public class PhanHoiEntity {

	@Id
	@GeneratedValue
	@Column(name = "ma_ph", columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "ma_benh_nhan")
	private BenhNhanEntity benhNhan;

	@Column(name = "tieu_de")
	private String tieuDe;

	@Column(name = "noi_dung", columnDefinition = "TEXT")
	private String noiDung;

	@Enumerated(EnumType.STRING)
	@Column(name = "loai_phan_hoi")
	@Builder.Default
	private LoaiPhanHoi loaiPhanHoi = LoaiPhanHoi.PHAN_NAN;


	@Column(name = "ngay_tao")
	private LocalDateTime ngayTao;

	@Enumerated(EnumType.STRING)
	@Column(name = "trang_thai")
	private TrangThai trangThai = TrangThai.MOI;

	@ManyToOne
	@JoinColumn(name = "tao_boi")
	private TaiKhoanEntity taoBoi;

	public enum LoaiPhanHoi {
	    PHAN_NAN,
	    DONG_VIEN,
	    GOP_Y,
	    CAU_HOI
	}
	public enum TrangThai {
		MOI, DA_XEM, DA_XU_LY
	}
}
