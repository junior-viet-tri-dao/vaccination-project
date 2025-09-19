package com.viettridao.vaccination.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.viettridao.vaccination.dto.response.employee.HoSoBenhAnResponse;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.DangKyTiemEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;

@Mapper(componentModel = "spring")
public interface HoSoBenhAnMapper {

	@Mapping(target = "maBenhNhan", source = "benhNhan.id")
	@Mapping(target = "hoTen", source = "benhNhan.hoTen")
	@Mapping(target = "gioiTinh", expression = "java(benhNhan.getGioiTinh() != null ? benhNhan.getGioiTinh().name() : null)")
	@Mapping(target = "tuoi", expression = "java(calculateTuoi(benhNhan.getNgaySinh()))")
	@Mapping(target = "nguoiGiamHo", source = "benhNhan.tenNguoiGiamHo")
	@Mapping(target = "diaChi", source = "benhNhan.diaChi")
	@Mapping(target = "soDienThoai", source = "benhNhan.soDienThoai")
	@Mapping(target = "vacXinDaTiem", expression = "java(mapKetQuaTiemList(ketQuaTiems))")
	@Mapping(target = "vacXinCanTiem", expression = "java(mapDangKyTiemList(dangKyTiems))")
	HoSoBenhAnResponse toResponse(BenhNhanEntity benhNhan, List<KetQuaTiemEntity> ketQuaTiems,
			List<DangKyTiemEntity> dangKyTiems);

	// tính tuổi từ ngày sinh
	default Integer calculateTuoi(LocalDate ngaySinh) {
		return ngaySinh != null ? Period.between(ngaySinh, LocalDate.now()).getYears() : null;
	}

	// helper convert LocalDateTime -> LocalDate
	default LocalDate toLocalDate(LocalDateTime dateTime) {
		return dateTime != null ? dateTime.toLocalDate() : null;
	}

	// Map danh sách vắc-xin đã tiêm
	@Named("mapKetQuaTiemList")
	default List<HoSoBenhAnResponse.VacXinDaTiem> mapKetQuaTiemList(List<KetQuaTiemEntity> ketQuaTiems) {
		if (ketQuaTiems == null)
			return null;
		return ketQuaTiems.stream()
				.map(kq -> HoSoBenhAnResponse.VacXinDaTiem.builder()
						.tenVacXin(kq.getLichTiem() != null && kq.getLichTiem().getVacXin() != null
								? kq.getLichTiem().getVacXin().getTen()
								: null)
						.thoiGianTiem(toLocalDate(kq.getNgayTiem())).phanUngSauTiem(kq.getPhanUngSauTiem()).build())
				.collect(Collectors.toList());
	}

	// Map danh sách vắc-xin cần tiêm
	@Named("mapDangKyTiemList")
	default List<HoSoBenhAnResponse.VacXinCanTiem> mapDangKyTiemList(List<DangKyTiemEntity> dangKyTiems) {
		if (dangKyTiems == null)
			return null;
		return dangKyTiems.stream()
				.map(dk -> HoSoBenhAnResponse.VacXinCanTiem.builder()
						.tenVacXin(dk.getLichTiem() != null && dk.getLichTiem().getVacXin() != null
								? dk.getLichTiem().getVacXin().getTen()
								: null)
						.thoiGianDuKien(dk.getLichTiem() != null ? toLocalDate(dk.getLichTiem().getNgayGio()) : null)
						.build())
				.collect(Collectors.toList());
	}
}
