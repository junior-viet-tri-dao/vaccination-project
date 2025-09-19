package com.viettridao.vaccination.dto.response.employee;

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
public class UpdateBenhNhanResponse {
	private String maBenhNhan;
	private String hoTen;
	private String gioiTinh;
	private Integer tuoi;
	private String tenNguoiGiamHo;
	private String diaChi;
	private String soDienThoai;
}
