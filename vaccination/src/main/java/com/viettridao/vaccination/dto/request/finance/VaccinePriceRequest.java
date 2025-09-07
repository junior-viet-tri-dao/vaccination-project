package com.viettridao.vaccination.dto.request.finance;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VaccinePriceRequest {

	@NotBlank(message = "Mã vắc xin không được để trống")
	@Size(max = 10, message = "Mã vắc xin không được vượt quá 10 ký tự")
	private String vaccineCode;

	@NotBlank(message = "Đơn vị không được để trống")
	@Size(max = 5, message = "Đơn vị không được vượt quá 5 ký tự")
	@Pattern(regexp = "^[\\p{L}\\s]+$", message = "Đơn vị chỉ được chứa chữ cái và khoảng trắng")
	private String unit;

	@NotBlank(message = "Năm sản xuất không được để trống")
	@Pattern(regexp = "^(19|20)\\d{2}$", message = "Năm sản xuất phải có định dạng yyyy (VD: 2024)")
	private String productionYear;

	@NotNull(message = "Giá không được để trống")
	@Positive(message = "Giá phải lớn hơn 0")
	@Min(value = 1000, message = "Giá phải từ 1,000 VNĐ trở lên")
	@Max(value = 100000000, message = "Giá không được vượt quá 100 triệu VNĐ")
	private Integer unitPrice;
}
