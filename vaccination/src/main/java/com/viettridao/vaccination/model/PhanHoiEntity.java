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
@Table(name = "phan_hoi")
public class PhanHoiEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_ph", columnDefinition = "CHAR(36)")
	private String id;

	private Boolean isDeleted = Boolean.FALSE;

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
