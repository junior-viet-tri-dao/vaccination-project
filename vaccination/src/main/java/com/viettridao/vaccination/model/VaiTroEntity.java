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
@Table(name = "vai_tro")
public class VaiTroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ma_vai_tro", columnDefinition = "CHAR(36)")
	private String id;

	@Column(name = "ten", nullable = false, unique = true)
	private String ten;

	@Column(name = "mo_ta")
	private String moTa;
	
	private Boolean isDeleted = Boolean.FALSE;

	@OneToMany(mappedBy = "vaiTro")
	private Set<TaiKhoanEntity> taiKhoans;
}
