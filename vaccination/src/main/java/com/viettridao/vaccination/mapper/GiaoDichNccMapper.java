package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.request.finance.GiaoDichNhaCungCapRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichNhaCungCapResponse;
import com.viettridao.vaccination.model.ChiTietHDNCCEntity;

@Mapper(componentModel = "spring")
public interface GiaoDichNccMapper {

	@Mapping(source = "hoaDonNCC.soHoaDon", target = "soHoaDon")
	@Mapping(source = "hoaDonNCC.ngayHD", target = "ngay")
	@Mapping(source = "soLo", target = "maLo")
	@Mapping(source = "vacXin.maCode", target = "maVacXin")
	@Mapping(source = "vacXin.ten", target = "tenVacXin")
	@Mapping(source = "soLuong", target = "soLuong")
	@Mapping(source = "hoaDonNCC.nhaCungCap.ten", target = "nhaCungCap")
	@Mapping(target = "gia", expression = "java(entity.getSoLuong() * entity.getDonGia())")
	GiaoDichNhaCungCapResponse toResponse(ChiTietHDNCCEntity entity);

	// Map từ request -> entity
	@Mapping(source = "soHoaDon", target = "hoaDonNCC.soHoaDon")
	@Mapping(source = "ngay", target = "hoaDonNCC.ngayHD")
	@Mapping(source = "maLo", target = "soLo")
	@Mapping(source = "maVacXin", target = "vacXin.maCode")
    @Mapping(source = "tenVacXin", target = "vacXin.ten") 
	@Mapping(source = "soLuong", target = "soLuong")
	@Mapping(source = "gia", target = "donGia")
	@Mapping(target = "tinhTrangNhapKho", expression = "java(ChiTietHDNCCEntity.TinhTrangNhapKho.CHUA_NHAP)")
	ChiTietHDNCCEntity toEntity(GiaoDichNhaCungCapRequest request);
}
