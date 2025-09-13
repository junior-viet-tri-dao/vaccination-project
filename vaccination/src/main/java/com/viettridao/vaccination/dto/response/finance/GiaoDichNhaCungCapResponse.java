package com.viettridao.vaccination.dto.response.finance;

import java.time.LocalDate;

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
public class GiaoDichNhaCungCapResponse {

	private String soHoaDon; // Mã hóa đơn NCC
	private LocalDate ngay; // Ngày hóa đơn
	private String maLo; // Mã lô vắc xin
	private String maVacXin; // Mã vắc xin
	private String tenVacXin; // Tên vắc xin
	private Integer soLuong; // Số lượng nhập
	private String nhaCungCap; // Tên nhà cung cấp
	private Integer gia; // Giá (VND)
}
