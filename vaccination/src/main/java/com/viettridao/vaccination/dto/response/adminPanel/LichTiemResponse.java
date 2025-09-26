package com.viettridao.vaccination.dto.response.adminPanel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
public class LichTiemResponse {

	private String maLich;

	private LocalDateTime ngayGio;

	private String diaDiem;

	private String tenVacXin;

	private Integer soLuong;

	private String doTuoiKhuyenCao;

	private String moTa;

	private List<BacSiDto> danhSachBacSi;

	private List<DonThuocDto> danhSachDonThuoc;

	// Nested DTO cho bác sĩ
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class BacSiDto {
		private String maBacSi;
		private String hoTen;
	}

	// Nested DTO cho đơn thuốc
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DonThuocDto {
		private String maDon;
		private String tenBenhNhan;
		private String soDienThoai;
		private LocalDate henTiemLai;
		private String tenVacXin;
	}
}
