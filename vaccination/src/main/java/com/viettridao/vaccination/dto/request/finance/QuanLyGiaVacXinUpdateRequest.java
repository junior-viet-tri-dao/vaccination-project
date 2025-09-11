package com.viettridao.vaccination.dto.request.finance;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
public class QuanLyGiaVacXinUpdateRequest {

	@NotBlank(message = "Mã vắc xin không được để trống")
	private String maCode; 

	@NotNull(message = "Ngày sản xuất không được để trống")
	@PastOrPresent(message = "Ngày sản xuất không được là ngày tương lai")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate namSX;

	@NotBlank(message = "Đơn vị không được để trống")
	private String donVi;

	@NotNull(message = "Giá không được để trống")
	@Positive(message = "Giá phải lớn hơn 0")
	private Integer gia;
}
