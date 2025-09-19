package com.viettridao.vaccination.dto.request.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UpdateBenhNhanRequest {

	@NotBlank(message = "ID bệnh nhân không được để trống")
	private String maBenhNhan;

	@NotBlank(message = "Họ và tên không được để trống")
	@Size(max = 255)
	private String hoTen;

	@NotBlank(message = "Giới tính không được để trống")
	@Pattern(regexp = "NAM|NU")
	private String gioiTinh;

	@NotNull(message = "Tuổi không được để trống")
	private Integer tuoi;

	@Size(max = 255)
	private String tenNguoiGiamHo;

	@Size(max = 255)
	private String diaChi;

	@Pattern(regexp = "\\d{10,11}", message = "Số điện thoại không hợp lệ")
	private String soDienThoai;
}
