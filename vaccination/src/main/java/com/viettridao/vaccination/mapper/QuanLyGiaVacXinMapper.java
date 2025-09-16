package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.viettridao.vaccination.dto.request.finance.QuanLyGiaVacXinUpdateRequest;
import com.viettridao.vaccination.dto.response.finance.QuanLyGiaVacXinResponse;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;

@Mapper(componentModel = "spring")
public interface QuanLyGiaVacXinMapper {

	// Map từ entity → response
	@Mapping(source = "loVacXin.vacXin.maCode", target = "maCode")
	@Mapping(source = "loVacXin.donVi", target = "donVi")
	@Mapping(source = "loVacXin.ngaySanXuat", target = "namSX")
	@Mapping(source = "bangGia.gia", target = "gia")
	QuanLyGiaVacXinResponse toResponse(LoVacXinEntity loVacXin, BangGiaVacXinEntity bangGia);

	// Map từ update request → LoVacXinEntity
	@Mapping(source = "donVi", target = "donVi")
	@Mapping(source = "namSX", target = "ngaySanXuat")
	void updateLoVacXinFromRequest(QuanLyGiaVacXinUpdateRequest request, @MappingTarget LoVacXinEntity loVacXin);

	// Map từ update request → BangGiaVacXinEntity
	@Mapping(source = "gia", target = "gia")
	void updateBangGiaFromRequest(QuanLyGiaVacXinUpdateRequest request, @MappingTarget BangGiaVacXinEntity bangGia);

	// Map từ create request → LoVacXinEntity (tạo mới)
	@Mapping(source = "maCode", target = "maLoCode") // nếu muốn tạo mã lô mới từ mã vaccine
	@Mapping(source = "donVi", target = "donVi")
	@Mapping(source = "namSX", target = "ngaySanXuat")
	LoVacXinEntity toNewLoVacXin(QuanLyGiaVacXinUpdateRequest request);

	// Map từ create request → BangGiaVacXinEntity (tạo mới)
	@Mapping(source = "gia", target = "gia")
	BangGiaVacXinEntity toNewBangGia(QuanLyGiaVacXinUpdateRequest request);
}
