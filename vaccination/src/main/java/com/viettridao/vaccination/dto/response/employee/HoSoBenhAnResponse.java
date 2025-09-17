package com.viettridao.vaccination.dto.response.employee;

import java.time.LocalDate;
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
public class HoSoBenhAnResponse {
	private String maBenhNhan;
	private String hoTen;
	private String gioiTinh;
	private Integer tuoi;
	private String nguoiGiamHo;
	private String diaChi;
	private String soDienThoai;
	private List<VacXinDaTiem> vacXinDaTiem;
	private List<VacXinCanTiem> vacXinCanTiem;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class VacXinDaTiem {
		private String tenVacXin;
		private LocalDate thoiGianTiem;
		private String phanUngSauTiem;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class VacXinCanTiem {
		private String tenVacXin;
		private LocalDate thoiGianDuKien;
	}
}
