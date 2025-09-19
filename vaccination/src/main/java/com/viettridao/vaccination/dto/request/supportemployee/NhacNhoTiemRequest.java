package com.viettridao.vaccination.dto.request.supportemployee;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
public class NhacNhoTiemRequest {

	@NotEmpty(message = "Email khách hàng không được để trống")
	@Email(message = "Email khách hàng không hợp lệ")
	private String email;

	@NotEmpty(message = "Danh sách thông tin tiêm không được để trống")
	private List<ThongTinTiemDto> lichTiem;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ThongTinTiemDto {

		@NotNull(message = "Ngày tiêm không được để trống")
		private LocalDateTime ngayTiem; // Ngày tiêm thực tế

		private String loaiVacXinDaTiem; // Loại vắc xin đã tiêm

		@NotNull(message = "Ngày dự kiến không được để trống")
		private LocalDateTime ngayDuKien; // Ngày tiêm dự kiến

		private String loaiVacXinDuKien; // Loại vắc xin dự kiến

		@NotNull(message = "Giá dự kiến không được để trống")
		private Integer gia; // Giá dự kiến
		
	    private boolean daGui; 

	}
}
