package com.viettridao.vaccination.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.viettridao.vaccination.dto.request.finance.GiaoDichKhachHangRequest;
import com.viettridao.vaccination.dto.response.finance.GiaoDichKhachHangResponse;
import com.viettridao.vaccination.model.ChiTietHDEntity;
import com.viettridao.vaccination.model.HoaDonEntity;

@Mapper(componentModel = "spring")
public interface GiaoDichKhachHangMapper {

	GiaoDichKhachHangMapper INSTANCE = Mappers.getMapper(GiaoDichKhachHangMapper.class);

	// Entity -> Response
	@Mapping(source = "hoaDon.ngayHD", target = "ngayHD")
	@Mapping(source = "hoaDon.soHoaDon", target = "soHoaDon")
	@Mapping(source = "chiTiet.vacXin.maCode", target = "maVacXin")
	@Mapping(source = "chiTiet.soLuong", target = "soLuong")
	@Mapping(source = "hoaDon.benhNhan.hoTen", target = "tenKhachHang")
    @Mapping(source = "chiTiet.thanhTien", target = "gia") 
	GiaoDichKhachHangResponse toResponse(HoaDonEntity hoaDon, ChiTietHDEntity chiTiet);

	List<GiaoDichKhachHangResponse> toResponseList(List<ChiTietHDEntity> chiTietList);

	// Request -> Entity
	@Mapping(source = "ngayHD", target = "ngayHD")
	@Mapping(source = "soHoaDon", target = "soHoaDon")
	@Mapping(source = "tenKhachHang", target = "benhNhan.hoTen")
	HoaDonEntity toHoaDonEntity(GiaoDichKhachHangRequest request);

	@Mapping(source = "maVacXin", target = "vacXin.maCode")
	@Mapping(source = "soLuong", target = "soLuong")
	@Mapping(source = "gia", target = "donGia")
	ChiTietHDEntity toChiTietEntity(GiaoDichKhachHangRequest request);
}
