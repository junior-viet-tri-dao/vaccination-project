package com.viettridao.vaccination.dto.response.supportemployee;

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
public class NhacNhoTiemResponse {
	private LocalDateTime ngayTiem; // Ngày tiêm thực tế
	private String loaiVacXinDaTiem; // Loại vắc xin đã tiêm
	private LocalDateTime ngayTiemDuKien; // Ngày dự kiến tiêm
	private String loaiVacXinDuKien; // Loại vắc xin dự kiến
	private Integer gia; // Giá dự kiến
	private String email; // Email khách hàng
	private String benhNhanId;
}
