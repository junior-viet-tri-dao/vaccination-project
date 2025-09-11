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
@Table(name = "don_thuoc")
public class DonThuocEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_don", columnDefinition = "CHAR(36)")
	private String id;

	private Boolean isDeleted = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "ma_benh_nhan", nullable = false)
	private BenhNhanEntity benhNhan;

	@ManyToOne
	@JoinColumn(name = "ke_boi")
	private TaiKhoanEntity keBoi;

	@Column(name = "ngay_ke")
	private LocalDateTime ngayKe;

	@ManyToOne
	@JoinColumn(name = "ma_vac_xin")
	private VacXinEntity vacXin;

	@Column(name = "hen_tiem_lai")
	private LocalDateTime henTiemLai;

	@Column(name = "ghi_chu")
	private String ghiChu;
}
