package com.viettridao.vaccination.dto.response.adminPanel;

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
	private String moTa;
	private Integer sucChua;
	private String tieuDe;

	private String loaiVacXin;
	private String tenVacXin;
	private String taoBoi;

	private List<DonThuocDTO> danhSachDonThuoc;
    private List<String> bacSiThamGia;


	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DonThuocDTO {
		private String maDon;
		private String tenBenhNhan;
		private Integer doTuoi;
		private String soDienThoai;
		private LocalDateTime henTiemLai;
		private String tenVacXin;
	}
}
