package com.viettridao.vaccination.mapper;

import java.util.List;

import com.viettridao.vaccination.dto.request.finance.UpdateVaccinePriceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.model.VaccineBatchEntity;

@Mapper(componentModel = "spring")
public interface VaccinePriceMapper {

	// Entity VaccineBatch -> Response
	@Mapping(source = "batchId", target = "batchId")
	@Mapping(target = "stt", ignore = true) // gán sau khi build list
	@Mapping(source = "vaccine.vaccineCode", target = "vaccineCode")
	@Mapping(source = "vaccine.unit", target = "unit")
	@Mapping(source = "productionYear", target = "productionYear")
	@Mapping(source = "vaccine.unitPrice", target = "unitPrice")
	VaccinePriceResponse toResponse(VaccineBatchEntity entity);

	List<VaccinePriceResponse> toResponseList(List<VaccineBatchEntity> entities);

	// Request -> VaccineBatch (chủ yếu dùng khi nhập giá mới)
	@Mapping(source = "productionYear", target = "productionYear")
	@Mapping(source = "unit", target = "vaccine.unit") // gán vào Vaccine bên trong
	@Mapping(source = "vaccineCode", target = "vaccine.vaccineCode")
	@Mapping(source = "unitPrice", target = "vaccine.unitPrice")
	@Mapping(target = "batchId", ignore = true) // sinh UUID
	@Mapping(target = "batchCode", ignore = true)
	@Mapping(target = "quantity", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "receivedDate", ignore = true)
	@Mapping(target = "countryOfOrigin", ignore = true)
	@Mapping(target = "licenseNumber", ignore = true)
	@Mapping(target = "supplier", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	VaccineBatchEntity toEntity(UpdateVaccinePriceRequest request);
}
