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
public class CauHoiTiemChungResponse {
	private String maCauHoi;
	private String noiDungCauHoi;
	private String noiDungTraLoi;
}
