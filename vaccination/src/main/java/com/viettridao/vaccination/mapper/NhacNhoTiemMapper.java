package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.request.supportemployee.NhacNhoTiemRequest.ThongTinTiemDto;
import com.viettridao.vaccination.dto.response.supportemployee.NhacNhoTiemResponse;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import com.viettridao.vaccination.model.LichTiemEntity;

@Mapper(componentModel = "spring")
public interface NhacNhoTiemMapper {

	@Mapping(source = "ketQuaTiem.ngayTiem", target = "ngayTiem")
	@Mapping(target = "loaiVacXinDaTiem", expression = "java(lichTiemDaTiem != null && lichTiemDaTiem.getVacXin() != null ? lichTiemDaTiem.getVacXin().getTen() : null)")
	@Mapping(source = "lichTiemDuKien.ngayGio", target = "ngayTiemDuKien")
	@Mapping(target = "loaiVacXinDuKien", expression = "java(lichTiemDuKien != null && lichTiemDuKien.getVacXin() != null ? lichTiemDuKien.getVacXin().getTen() : null)")
	@Mapping(target = "gia", expression = "java(bangGia != null ? bangGia.getGia() : null)")
	@Mapping(target = "email", expression = "java(benhNhan != null ? benhNhan.getEmail() : null)")
	@Mapping(target = "benhNhanId", expression = "java(benhNhan != null ? benhNhan.getId() : null)")
	NhacNhoTiemResponse toResponse(KetQuaTiemEntity ketQuaTiem, LichTiemEntity lichTiemDaTiem,
			LichTiemEntity lichTiemDuKien, BangGiaVacXinEntity bangGia, BenhNhanEntity benhNhan);

	@Mapping(source = "ketQuaTiem.ngayTiem", target = "ngayTiem")
	@Mapping(target = "loaiVacXinDaTiem", expression = "java(lichTiemDaTiem != null && lichTiemDaTiem.getVacXin() != null ? lichTiemDaTiem.getVacXin().getTen() : null)")
	@Mapping(source = "lichTiemDuKien.ngayGio", target = "ngayDuKien")
	@Mapping(target = "loaiVacXinDuKien", expression = "java(lichTiemDuKien != null && lichTiemDuKien.getVacXin() != null ? lichTiemDuKien.getVacXin().getTen() : null)")
	@Mapping(target = "gia", expression = "java(bangGia != null ? bangGia.getGia() : null)")
	ThongTinTiemDto toRequestDto(KetQuaTiemEntity ketQuaTiem, LichTiemEntity lichTiemDaTiem,
			LichTiemEntity lichTiemDuKien, BangGiaVacXinEntity bangGia);
}
