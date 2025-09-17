package com.viettridao.vaccination.dto.response.supportemployee;

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
public class GiaiDapThacMacResponse {
	private String maPh;
	private String cauHoi;
	private String traLoi;
	private String emailBenhNhan;
	private String trangThai;
}
