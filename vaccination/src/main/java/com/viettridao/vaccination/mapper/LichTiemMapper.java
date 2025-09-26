package com.viettridao.vaccination.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse.BacSiDto;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse.DonThuocDto;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Mapper(componentModel = "spring")
public interface LichTiemMapper {

	// ===== Request → Entity =====
	@Mapping(target = "id", ignore = true) 
	@Mapping(target = "vacXin", source = "maVacXin", qualifiedByName = "mapVacXin")
	@Mapping(target = "bacSis", source = "danhSachBacSiIds", qualifiedByName = "mapBacSiSet")
	@Mapping(target = "danhSachDonThuoc", ignore = true) 
	@Mapping(target = "dangKyTiems", ignore = true)
	@Mapping(target = "ketQuaTiems", ignore = true)
	@Mapping(target = "taoBoi", ignore = true) 
	@Mapping(target = "ngayTao", ignore = true) 
	@Mapping(target = "ngayGio", source = "ngayGio")
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "tieuDe", ignore = true)
	@Mapping(target = "moTa", source = "moTa")
	@Mapping(target = "sucChua", source = "soLuong")
	LichTiemEntity toEntity(LichTiemRequest request);

	// ===== Entity → Response =====
	@Mapping(target = "maLich", source = "id")
	@Mapping(target = "tenVacXin", source = "vacXin.ten")
	@Mapping(target = "danhSachBacSi", source = "bacSis", qualifiedByName = "mapBacSiDtoList")
	@Mapping(target = "danhSachDonThuoc", source = "danhSachDonThuoc", qualifiedByName = "mapDonThuocList")
	LichTiemResponse toResponse(LichTiemEntity entity);

	// ===== Helper mapping =====

	@Named("mapVacXin")
	default VacXinEntity mapVacXin(String maVacXin) {
		if (maVacXin == null)
			return null;
		VacXinEntity vacXin = new VacXinEntity();
		vacXin.setId(maVacXin);
		return vacXin;
	}

	@Named("mapBacSiSet")
	default Set<TaiKhoanEntity> mapBacSiSet(List<String> ids) {
		if (ids == null)
			return null;
		return ids.stream().map(id -> {
			TaiKhoanEntity bacSi = new TaiKhoanEntity();
			bacSi.setId(id);
			return bacSi;
		}).collect(Collectors.toSet());
	}

	@Named("mapBacSiDtoList")
	default List<BacSiDto> mapBacSiDtoList(Set<TaiKhoanEntity> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(e -> BacSiDto.builder().maBacSi(e.getId()).hoTen(e.getHoTen()).build())
				.collect(Collectors.toList());
	}

	@Named("mapDonThuocList")
	default List<DonThuocDto> mapDonThuocList(List<DonThuocEntity> entities) {
		if (entities == null)
			return null;
		return entities.stream()
				.map(d -> DonThuocDto.builder().maDon(d.getId()).tenBenhNhan(d.getBenhNhan().getHoTen())
						.soDienThoai(d.getBenhNhan().getSoDienThoai())
						.henTiemLai(d.getHenTiemLai() != null ? d.getHenTiemLai().toLocalDate() : null) 
						.tenVacXin(d.getVacXin().getTen()).build())
				.collect(Collectors.toList());
	}

	List<LichTiemResponse> toResponseList(List<LichTiemEntity> entities);
}
