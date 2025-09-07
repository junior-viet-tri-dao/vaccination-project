package com.viettridao.vaccination.dto.response.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinePriceResponse {
	private String batchId; // để check chọn (radio/checkbox)
	private Integer stt; // số thứ tự
	private String vaccineCode; // mã vắc xin
	private String unit; // đơn vị
	private String productionYear;// năm sản xuất
	private Double unitPrice;
}
