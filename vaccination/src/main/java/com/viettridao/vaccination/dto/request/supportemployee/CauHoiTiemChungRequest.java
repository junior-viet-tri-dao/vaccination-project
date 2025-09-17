package com.viettridao.vaccination.dto.request.supportemployee;

import jakarta.validation.constraints.NotBlank;
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
public class CauHoiTiemChungRequest {
	@NotBlank(message = "Nội dung câu hỏi không được để trống")
	private String noiDungCauHoi;

	@NotBlank(message = "Nội dung trả lời không được để trống")
	private String noiDungTraLoi;
}
