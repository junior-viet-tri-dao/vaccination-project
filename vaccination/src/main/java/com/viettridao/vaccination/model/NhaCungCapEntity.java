package com.viettridao.vaccination.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "nha_cung_cap")
public class NhaCungCapEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_ncc", columnDefinition = "CHAR(36)")
	private String id;

	@Column(name = "ten", nullable = false)
	private String ten;

	@Column(name = "nguoi_lien_he")
	private String nguoiLienHe;

	@Column(name = "so_dien_thoai")
	private String soDienThoai;

	@Column(name = "email")
	private String email;

	@Column(name = "dia_chi")
	private String diaChi;

	private Boolean isDeleted = Boolean.FALSE;
	
	@OneToMany(mappedBy = "nhaCungCap")
	private Set<LoVacXinEntity> loVacXins;

	@OneToMany(mappedBy = "nhaCungCap")
	private Set<HoaDonNCCEntity> hoaDonNCCs;
}
