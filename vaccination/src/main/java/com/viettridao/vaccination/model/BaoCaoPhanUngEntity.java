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
@Table(name = "bao_cao_phan_ung")
public class BaoCaoPhanUngEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_bc", columnDefinition = "CHAR(36)")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ma_benh_nhan")
	private BenhNhanEntity benhNhan;

	@ManyToOne
	@JoinColumn(name = "ma_vac_xin")
	private VacXinEntity vacXin;

	@Column(name = "thoi_gian")
	private LocalDateTime thoiGian;

	@Column(name = "mo_ta", columnDefinition = "TEXT")
	private String moTa;

	@Enumerated(EnumType.STRING)
	@Column(name = "kenh_bao_cao")
	private KenhBaoCao kenhBaoCao = KenhBaoCao.NGUOI_DUNG;

	@ManyToOne
	@JoinColumn(name = "tao_boi")
	private TaiKhoanEntity taoBoi;
	
	private Boolean isDeleted = Boolean.FALSE;

	public enum KenhBaoCao {
		NGUOI_DUNG, NHAN_VIEN
	}
}
