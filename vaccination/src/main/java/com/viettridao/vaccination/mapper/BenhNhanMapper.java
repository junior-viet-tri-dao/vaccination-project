package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.request.employee.UpdateBenhNhanRequest;
import com.viettridao.vaccination.dto.response.employee.UpdateBenhNhanResponse;
import com.viettridao.vaccination.model.BenhNhanEntity;

@Mapper(componentModel = "spring")
public interface BenhNhanMapper {

	// Map UpdateBenhNhanRequest -> BenhNhanEntity
	@Mapping(target = "id", source = "maBenhNhan")
	@Mapping(target = "hoTen", source = "hoTen")
	@Mapping(target = "gioiTinh", expression = "java(com.viettridao.vaccination.model.BenhNhanEntity.GioiTinh.valueOf(request.getGioiTinh()))")
	@Mapping(target = "ngaySinh", expression = "java(java.time.LocalDate.now().minusYears(request.getTuoi()))")
	@Mapping(target = "tenNguoiGiamHo", source = "tenNguoiGiamHo")
	@Mapping(target = "diaChi", source = "diaChi")
	@Mapping(target = "soDienThoai", source = "soDienThoai")
	BenhNhanEntity toEntity(UpdateBenhNhanRequest request);

	// Map BenhNhanEntity -> UpdateBenhNhanResponse
	@Mapping(target = "maBenhNhan", source = "id")
	@Mapping(target = "hoTen", source = "hoTen")
	@Mapping(target = "gioiTinh", expression = "java(entity.getGioiTinh().name())")
	@Mapping(target = "tuoi", expression = "java(java.time.LocalDate.now().getYear() - entity.getNgaySinh().getYear())")
	@Mapping(target = "tenNguoiGiamHo", source = "tenNguoiGiamHo")
	@Mapping(target = "diaChi", source = "diaChi")
	@Mapping(target = "soDienThoai", source = "soDienThoai")
	UpdateBenhNhanResponse toResponse(BenhNhanEntity entity);
}
