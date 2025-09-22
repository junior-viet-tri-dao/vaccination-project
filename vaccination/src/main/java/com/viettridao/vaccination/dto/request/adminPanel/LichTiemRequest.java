package com.viettridao.vaccination.dto.request.adminPanel;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class LichTiemRequest {

	@NotNull(message = "Ngày giờ tiêm không được để trống")
	private LocalDateTime ngayGio; // Ngày giờ tiêm

	@NotBlank(message = "Địa điểm tiêm không được để trống")
	@Size(max = 255, message = "Địa điểm tiêm không được quá 255 ký tự")
	private String diaDiem; // Địa điểm tiêm

	@Size(max = 1000, message = "Mô tả không được quá 1000 ký tự")
	private String moTa; // Ghi chú

	@NotNull(message = "Sức chứa không được để trống")
	@Min(value = 1, message = "Sức chứa phải lớn hơn 0")
	private Integer sucChua; // Số lượng vắc-xin

	@NotBlank(message = "Tiêu đề không được để trống")
	@Size(max = 255, message = "Tiêu đề không được quá 255 ký tự")
	private String tieuDe; // Tiêu đề

	@NotBlank(message = "Loại vắc-xin không được để trống")
	private String maVacXin; // Loại vắc-xin (ID)

	@NotBlank(message = "Người tạo lịch không được để trống")
	private String taoBoi; // ID bác sĩ hoặc nhân viên tạo lịch

	// Thông tin bổ sung UI (có thể dùng nếu cần)
	@Size(max = 1000, message = "Ghi chú không được quá 1000 ký tự")
	private String ghiChu;

	@Size(max = 255, message = "Loại vắc-xin không được quá 255 ký tự")
	private String loaiVacXin;

	@Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
	private Integer soLuong;

	@Min(value = 0, message = "Độ tuổi phải lớn hơn hoặc bằng 0")
	private String doTuoi;
}
