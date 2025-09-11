package com.viettridao.vaccination.dto.request.finance;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class GiaoDichKhachHangRequest {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@NotNull(message = "Ngày hóa đơn không được để trống")
	private LocalDate ngayHD;


	@NotBlank(message = "Mã hóa đơn không được để trống")
	private String maHoaDon;

	@NotBlank(message = "Mã vắc xin không được để trống")
	private String maVacXin;

	@NotNull(message = "Số lượng không được để trống")
	@Positive(message = "Số lượng phải > 0")
	private Integer soLuong;

	@NotBlank(message = "Tên khách hàng không được để trống")
	private String tenKhachHang;

	@NotNull(message = "Giá không được để trống")
	@PositiveOrZero(message = "Giá phải >= 0")
	private Integer gia;
}
