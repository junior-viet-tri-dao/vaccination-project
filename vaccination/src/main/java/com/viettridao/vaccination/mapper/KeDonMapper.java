package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.viettridao.vaccination.dto.request.employee.KeDonRequest;
import com.viettridao.vaccination.dto.response.employee.KeDonResponse;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Mapper(componentModel = "spring")
public interface KeDonMapper {

	KeDonMapper INSTANCE = Mappers.getMapper(KeDonMapper.class);

	// ----------------------------
	// Request -> Entity
	// ----------------------------
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "benhNhan", source = "maBenhNhan", qualifiedByName = "mapBenhNhan")
	@Mapping(target = "vacXin", source = "maVacXin", qualifiedByName = "mapVacXin")
	@Mapping(target = "keBoi", ignore = true) // sẽ set trong service từ user login
	@Mapping(target = "ngayKe", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "ghiChu", ignore = true)
	@Mapping(target = "isDeleted", constant = "false")
	DonThuocEntity toEntity(KeDonRequest request);

	// ----------------------------
	// Entity -> Response
	// ----------------------------
	@Mapping(target = "maBenhNhan", source = "benhNhan.id")
	@Mapping(target = "maVacXin", source = "vacXin.id")
	KeDonResponse toResponse(DonThuocEntity entity);

	// ----------------------------
	// Helpers
	// ----------------------------
	@Named("mapBenhNhan")
	default BenhNhanEntity mapBenhNhan(String maBenhNhan) {
		if (maBenhNhan == null)
			return null;
		return BenhNhanEntity.builder().id(maBenhNhan).build();
	}

	@Named("mapVacXin")
	default VacXinEntity mapVacXin(String maVacXin) {
		if (maVacXin == null)
			return null;
		return VacXinEntity.builder().id(maVacXin).build();
	}
}
