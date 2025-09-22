package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.viettridao.vaccination.dto.request.employee.KetQuaTiemRequest;
import com.viettridao.vaccination.dto.response.employee.KetQuaTiemResponse;
import com.viettridao.vaccination.model.KetQuaTiemEntity;

@Mapper(componentModel = "spring")
public interface KetQuaTiemMapper {

    // Entity -> Response
    @Mapping(source = "id", target = "maKq")
    @Mapping(source = "benhNhan.hoTen", target = "tenBenhNhan")
    @Mapping(source = "lichTiem.vacXin.ten", target = "tenVacXin")
    @Mapping(source = "ngayTiem", target = "ngayTiem")
    @Mapping(source = "nguoiThucHien.hoTen", target = "nguoiThucHien")
    @Mapping(source = "tinhTrang", target = "tinhTrang")
    @Mapping(source = "phanUngSauTiem", target = "phanUngSauTiem")
    @Mapping(source = "ghiChu", target = "ghiChu")
    @Mapping(source = "lichTiem.diaDiem", target = "diaDiem")
    KetQuaTiemResponse toResponse(KetQuaTiemEntity ketQuaTiem);

    @Mapping(source = "tenBenhNhan", target = "tenBenhNhan")
    @Mapping(source = "tenVacXin", target = "tenVacXin")
    @Mapping(source = "ngayTiem", target = "ngayTiem")
    @Mapping(source = "nguoiThucHien", target = "nguoiThucHien")
    @Mapping(source = "tinhTrang", target = "tinhTrang")
    @Mapping(source = "phanUngSauTiem", target = "phanUngSauTiem")
    @Mapping(source = "ghiChu", target = "ghiChu")
    KetQuaTiemResponse requestToResponse(KetQuaTiemRequest request);

}