package com.viettridao.vaccination.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "bang_gia_vac_xin")
public class BangGiaVacXinEntity {

	@Id
	@GeneratedValue
	@Column(name = "ma_bg", columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "ma_vac_xin", nullable = false)
	private VacXinEntity vacXin;

	@Column(name = "gia", nullable = false)
	private Integer gia;

	@Column(name = "hieu_luc_tu")
	private LocalDate hieuLucTu;

	@Column(name = "hieu_luc_den")
	private LocalDate hieuLucDen;

	@ManyToOne
	@JoinColumn(name = "tao_boi")
	private TaiKhoanEntity taoBoi;

	@Column(name = "ngay_tao")
	private LocalDateTime ngayTao;
}
