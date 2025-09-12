package com.viettridao.vaccination.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "hoa_don_ncc")
public class HoaDonNCCEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_hd_ncc", columnDefinition = "CHAR(36)")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ma_ncc", nullable = false)
	private NhaCungCapEntity nhaCungCap;

	@Column(name = "so_hoa_don", nullable = false)
	private String soHoaDon;

	@Column(name = "ngay_hd")
	private LocalDate ngayHD;

	@Column(name = "tong_tien")
	private Integer tongTien;

	@ManyToOne
	@JoinColumn(name = "tao_boi")
	private TaiKhoanEntity taoBoi;

	@Column(name = "ngay_tao")
	private LocalDateTime ngayTao;

	private Boolean isDeleted = Boolean.FALSE;

	@OneToMany(mappedBy = "hoaDonNCC")
	private Set<ChiTietHDNCCEntity> chiTietHDNCCs;
}
