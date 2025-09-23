package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiSauTiemRequest;
import com.viettridao.vaccination.dto.response.normalUser.PhanHoiSauTiemResponse;
import com.viettridao.vaccination.model.BaoCaoPhanUngEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;

@Mapper(componentModel = "spring")
public interface PhanHoiSauTiemMapper {

	// Request -> Entity
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "benhNhan", ignore = true)
	@Mapping(target = "vacXin", ignore = true)
	@Mapping(target = "ketQuaTiem", ignore = true)
	@Mapping(target = "taoBoi", ignore = true)
	@Mapping(target = "thoiGian", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "kenhBaoCao", constant = "BENH_NHAN")
	@Mapping(target = "isDeleted", constant = "false")
	@Mapping(target = "trangThaiPhanHoi", constant = "CHUA_PHAN_HOI")
	BaoCaoPhanUngEntity toEntity(PhanHoiSauTiemRequest request);

	// BaoCaoPhanUngEntity -> Response
	@Mapping(target = "tenVacXin", source = "vacXin.ten")
	@Mapping(target = "thoiGian", source = "ketQuaTiem.lichTiem.ngayGio")
	@Mapping(target = "diaDiem", source = "ketQuaTiem.lichTiem.diaDiem")
	@Mapping(target = "nhanVienPhuTrach", source = "ketQuaTiem.nguoiThucHien.hoTen")
	@Mapping(target = "moTa", source = "moTa")
	@Mapping(target = "ketQuaTiemId", source = "ketQuaTiem.id")
	@Mapping(target = "trangThaiPhanHoi", expression = "java(entity.getTrangThaiPhanHoi() != null ? entity.getTrangThaiPhanHoi().name() : null)")
	PhanHoiSauTiemResponse toResponse(BaoCaoPhanUngEntity entity);

	// KetQuaTiemEntity -> Response (cho danh sách chưa phản hồi)
	@Mapping(target = "tenVacXin", source = "lichTiem.vacXin.ten")
	@Mapping(target = "thoiGian", source = "lichTiem.ngayGio")
	@Mapping(target = "diaDiem", source = "lichTiem.diaDiem")
	@Mapping(target = "nhanVienPhuTrach", source = "nguoiThucHien.hoTen")
	@Mapping(target = "moTa", ignore = true) // vì chưa có phản hồi
	@Mapping(target = "ketQuaTiemId", source = "id")
	@Mapping(target = "trangThaiPhanHoi", constant = "CHUA_PHAN_HOI")
	PhanHoiSauTiemResponse toResponse(KetQuaTiemEntity entity);

	// Helper convert LocalDate/LocalDateTime -> String
	default String map(java.time.LocalDateTime dateTime) {
		return dateTime != null ? dateTime.toString() : null;
	}

	default String map(java.time.LocalDate date) {
		return date != null ? date.toString() : null;
	}
}
