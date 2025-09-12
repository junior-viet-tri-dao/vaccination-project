package com.viettridao.vaccination.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "chi_tiet_hd")
public class ChiTietHDEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_ct", columnDefinition = "CHAR(36)")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ma_hd", nullable = false)
	private HoaDonEntity hoaDon;

	@ManyToOne
	@JoinColumn(name = "ma_vac_xin")
	private VacXinEntity vacXin;

	@ManyToOne
	@JoinColumn(name = "ma_lo")
	private LoVacXinEntity loVacXin;

	@Column(name = "so_luong", nullable = false)
	private Integer soLuong;

	@Column(name = "don_gia")
	private Integer donGia;

	@Column(name = "thanh_tien")
	private Integer thanhTien;

	private Boolean isDeleted = Boolean.FALSE;
}
