package com.viettridao.vaccination.dto.response.finance;

import java.time.LocalDate;

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
public class QuanLyGiaVacXinResponse {
	private String maCode; // Mã vắc xin (VacXinEntity.id)
	private String donVi; // Đơn vị (LoVacXinEntity.donVi)
	private LocalDate namSX; // Năm sản xuất (LoVacXinEntity.ngaySanXuat)
	private Integer gia; // Giá (BangGiaVacXinEntity.gia)
}
