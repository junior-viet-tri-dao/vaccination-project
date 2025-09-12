package com.viettridao.vaccination.dto.response.finance;

import java.time.LocalDateTime;

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
public class GiaoDichKhachHangResponse {

	private LocalDateTime ngayHD; // Ngày hóa đơn
	private String soHoaDon; // Mã Hóa Đơn
	private String maVacXin; // Mã Vắc xin
	private Integer soLuong; // Số lượng
	private String tenKhachHang; // Tên khách hàng
	private Integer gia; // Giá (VND)

}
