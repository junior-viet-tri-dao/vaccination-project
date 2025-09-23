package com.viettridao.vaccination.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.LichTiemEntity;

@Mapper(componentModel = "spring")
public interface LichTiemMapper {

	// LichTiemRequest -> LichTiemEntity
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "vacXin", ignore = true)
	@Mapping(target = "taoBoi", ignore = true)
    @Mapping(target = "bacSis", ignore = true) // map trong service
	@Mapping(target = "dangKyTiems", ignore = true) // map trong service nếu cần
	LichTiemEntity toEntity(LichTiemRequest request);

	// LichTiemEntity -> LichTiemResponse
	@Mapping(target = "maLich", source = "id")
	@Mapping(target = "ngayGio", source = "ngayGio")
	@Mapping(target = "diaDiem", source = "diaDiem")
	@Mapping(target = "moTa", source = "moTa")
	@Mapping(target = "sucChua", source = "sucChua")
	@Mapping(target = "tieuDe", source = "tieuDe")
	@Mapping(target = "loaiVacXin", source = "vacXin.loai")
	@Mapping(target = "tenVacXin", source = "vacXin.ten")
	@Mapping(target = "taoBoi", source = "taoBoi.hoTen")
	@Mapping(target = "danhSachDonThuoc", source = "dangKyTiems")
    @Mapping(target = "bacSiThamGia", expression = "java(entity.getBacSis().stream().map(TaiKhoanEntity::getHoTen).toList())")
	LichTiemResponse toResponse(LichTiemEntity entity);

	// DonThuocEntity -> DonThuocDTO
	@Mapping(target = "maDon", source = "id")
	@Mapping(target = "tenBenhNhan", source = "benhNhan.hoTen")
	@Mapping(target = "doTuoi", source = "benhNhan.ngaySinh", qualifiedByName = "calculateAge")
	@Mapping(target = "soDienThoai", source = "benhNhan.soDienThoai")
	@Mapping(target = "henTiemLai", source = "henTiemLai")
	@Mapping(target = "tenVacXin", source = "vacXin.ten")
	LichTiemResponse.DonThuocDTO donThuocEntityToDTO(DonThuocEntity donThuocEntity);

	List<LichTiemResponse.DonThuocDTO> donThuocEntityListToDTOList(List<DonThuocEntity> donThuocEntities);

	// Tính tuổi từ ngày sinh
	@Named("calculateAge")
	default Integer calculateAge(LocalDate ngaySinh) {
		if (ngaySinh == null)
			return null;
		return Period.between(ngaySinh, LocalDate.now()).getYears();
	}
}
