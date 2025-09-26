package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.adminPanel.TaiKhoanCreateRequest;
import com.viettridao.vaccination.model.TaiKhoanEntity;
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

    // Map trực tiếp từ request, dùng khi bạn tạo tài khoản và bệnh nhân cùng lúc
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ngayTao", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "taoBoiTaiKhoan", ignore = true)
    @Mapping(target = "taiKhoan", ignore = true)
    @Mapping(target = "gioiTinh", ignore = true) // hoặc default như NAM
    @Mapping(target = "ngaySinh", ignore = true)
    @Mapping(target = "tenNguoiGiamHo", ignore = true)
    @Mapping(target = "soDienThoai", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "hoSoBenhAns", ignore = true)
    @Mapping(target = "dangKyTiems", ignore = true)
    @Mapping(target = "hoaDons", ignore = true)
    @Mapping(target = "donThuocs", ignore = true)
    @Mapping(target = "baoCaoPhanUngs", ignore = true)
    @Mapping(target = "phanHois", ignore = true)
    @Mapping(target = "ketQuaTiems", ignore = true)
    BenhNhanEntity toBenhNhanEntity(TaiKhoanCreateRequest request);

    // Map từ TaiKhoanEntity sang BenhNhanEntity nếu cần
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ngayTao", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "taoBoiTaiKhoan", ignore = true)
    @Mapping(target = "taiKhoan", source = "entity")
    @Mapping(target = "hoTen", source = "entity.hoTen")
    @Mapping(target = "diaChi", source = "entity.diaChi")
    @Mapping(target = "gioiTinh", ignore = true)
    @Mapping(target = "ngaySinh", ignore = true)
    @Mapping(target = "tenNguoiGiamHo", ignore = true)
    @Mapping(target = "soDienThoai", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "hoSoBenhAns", ignore = true)
    @Mapping(target = "dangKyTiems", ignore = true)
    @Mapping(target = "hoaDons", ignore = true)
    @Mapping(target = "donThuocs", ignore = true)
    @Mapping(target = "baoCaoPhanUngs", ignore = true)
    @Mapping(target = "phanHois", ignore = true)
    @Mapping(target = "ketQuaTiems", ignore = true)
    BenhNhanEntity fromTaiKhoan(TaiKhoanEntity entity);
}
