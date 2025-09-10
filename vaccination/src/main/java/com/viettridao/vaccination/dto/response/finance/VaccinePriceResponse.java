package com.viettridao.vaccination.dto.response.finance;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinePriceResponse {
	private UUID vaccineId; // ID vắc xin
	private String maCode; // Mã vắc xin
	private String ten; // Tên vắc xin
	private String donVi; // Đơn vị (lấy từ LoVacXinEntity.hamLuong hoặc cột riêng)
	private Integer namSanXuat; // Năm sản xuất (LoVacXinEntity.ngaySanXuat)
	private Integer gia;
}
