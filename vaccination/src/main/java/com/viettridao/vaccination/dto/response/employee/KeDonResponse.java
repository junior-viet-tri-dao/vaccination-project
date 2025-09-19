package com.viettridao.vaccination.dto.response.employee;

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
public class KeDonResponse {
	private String maBenhNhan;
	private String maVacXin;
	private LocalDateTime henTiemLai;
}
