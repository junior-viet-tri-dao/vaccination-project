package com.viettridao.vaccination.model;

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
@Table(name = "hoa_don")
public class HoaDonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_hd", columnDefinition = "CHAR(36)")
	private String id;

	@Column(name = "so_hoa_don", nullable = false, unique = true)
	private String soHoaDon;

	@ManyToOne
	@JoinColumn(name = "ma_benh_nhan")
	private BenhNhanEntity benhNhan;

	@ManyToOne
	@JoinColumn(name = "tao_boi")
	private TaiKhoanEntity taoBoi;

	@Column(name = "ngay_hd")
	private LocalDateTime ngayHD;

	@Column(name = "tong_tien")
	private Integer tongTien;

	@Column(name = "ghi_chu")
	private String ghiChu;

	private Boolean isDeleted = Boolean.FALSE;

	@OneToMany(mappedBy = "hoaDon")
	private Set<ChiTietHDEntity> chiTietHDs;


}
