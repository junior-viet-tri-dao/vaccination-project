package com.viettridao.vaccination.model;

import java.util.Set;

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

	// Many-to-Many với Quyền hạn
	@ManyToMany
	@JoinTable(
			name = "vai_tro_quyen_han", // bảng trung gian
			joinColumns = @JoinColumn(name = "ma_vai_tro"), // FK đến vai_tro
			inverseJoinColumns = @JoinColumn(name = "ma_quyen_han") // FK đến quyen_han
	)
	private Set<QuyenHanEntity> quyenHans;
}
