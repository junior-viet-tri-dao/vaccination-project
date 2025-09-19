package com.viettridao.vaccination.dto.request.supportemployee;

import jakarta.validation.constraints.Email;
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
public class GiaiDapThacMacRequest {

	@NotBlank(message = "Mã phản hồi không được để trống")
	private String maPh;

	@NotBlank(message = "Nội dung trả lời không được để trống")
	private String traLoi; // Nội dung trả lời

	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không hợp lệ")
	private String emailBenhNhan; // Email của khách hàng

}
