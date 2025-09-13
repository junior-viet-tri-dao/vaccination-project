package com.viettridao.vaccination.dto.request.finance;

import java.time.LocalDate;

import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class GiaoDichNhaCungCapRequest {

	@NotBlank(message = "Mã hóa đơn không được để trống")
	private String soHoaDon;

	@NotBlank(message = "Tên vắc xin không được để trống")
	private String tenVacXin;

	@NotNull(message = "Ngày hóa đơn không được để trống")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@PastOrPresent(message = "Ngày hóa đơn không được là tương lai")
	private LocalDate ngay;

	@NotBlank(message = "Mã lô vắc xin không được để trống")
	private String maLo;

	@NotBlank(message = "Mã vắc xin không được để trống")
	private String maVacXin;

	@NotNull(message = "Số lượng nhập không được để trống")
	@Min(value = 1, message = "Số lượng phải lớn hơn 0")
	private Integer soLuong;

	@NotNull(message = "Đơn giá không được để trống")
	@Min(value = 100, message = "Đơn giá phải lớn hơn 100 VND")
	private Integer gia; 

	@NotBlank(message = "Tên nhà cung cấp không được để trống")
	private String tenNhaCungCap;

}
