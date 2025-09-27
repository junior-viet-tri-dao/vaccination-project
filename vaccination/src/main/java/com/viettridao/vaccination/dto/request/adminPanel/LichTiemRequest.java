package com.viettridao.vaccination.dto.request.adminPanel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.Future;
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
	@Future(message = "Ngày giờ tiêm phải lớn hơn thời điểm hiện tại")
	private LocalDateTime ngayGio;

	@NotBlank(message = "Địa điểm không được để trống")
	@Size(max = 255, message = "Địa điểm tối đa 255 ký tự")
	private String diaDiem;

	@NotBlank(message = "Tên vắc xin không được để trống")
	private String maVacXin;

	@NotNull(message = "Số lượng vắc xin phải có")
	@Min(value = 1, message = "Số lượng vắc xin phải lớn hơn 0")
	private Integer soLuong;

	@NotBlank(message = "Độ tuổi khuyến cáo không được để trống")
	private String doTuoiKhuyenCao;

	@Size(max = 500, message = "Ghi chú tối đa 500 ký tự")
	private String moTa;

	@NotNull(message = "Phải chọn ít nhất 1 bác sĩ")
	private List<String> danhSachBacSiIds;

	public String getGioString() {
		if (ngayGio != null) {
			return ngayGio.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		return "";
	}

}