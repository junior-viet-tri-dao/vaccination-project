package com.viettridao.vaccination.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.response.supportemployee.CauHoiTiemChungResponse;
import com.viettridao.vaccination.model.PhanHoiEntity;

@Mapper(componentModel = "spring")
public interface CauHoiTiemChungMapper {

	@Mapping(source = "id", target = "maCauHoi")
	@Mapping(source = "tieuDe", target = "noiDungCauHoi")
	@Mapping(source = "noiDung", target = "noiDungTraLoi")
	CauHoiTiemChungResponse toResponse(PhanHoiEntity entity);

	List<CauHoiTiemChungResponse> toResponseList(List<PhanHoiEntity> entities);
}
