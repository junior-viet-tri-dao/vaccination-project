package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.viettridao.vaccination.dto.request.adminPanel.TaiKhoanCreateRequest;
import com.viettridao.vaccination.model.TaiKhoanEntity;

@Mapper(componentModel = "spring")
public interface TaiKhoanMapper {

    // Map TaiKhoanCreateRequest -> TaiKhoanEntity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenDangNhap", source = "tenDangNhap")
    // Mật khẩu sẽ được mã hóa ở Service, không map ở đây
    @Mapping(target = "matKhauHash", ignore = true)
    // Vai trò sẽ set ở Service do là list
    @Mapping(target = "vaiTro", ignore = true)
    @Mapping(target = "hoTen", source = "hoTen")
    @Mapping(target = "soCmnd", source = "soCmnd")
    @Mapping(target = "soDienThoai", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "diaChi", source = "diaChi")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "hoatDong", constant = "true")
    @Mapping(target = "ngayTao", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "ngayCapNhat", ignore = true)
    @Mapping(target = "benhNhans", ignore = true)
    @Mapping(target = "lichTiems", ignore = true)
    @Mapping(target = "hoSoBenhAns", ignore = true)
    @Mapping(target = "hoaDonNCCs", ignore = true)
    @Mapping(target = "hoaDons", ignore = true)
    @Mapping(target = "bienDongKhos", ignore = true)
    @Mapping(target = "dangKyTiems", ignore = true)
    @Mapping(target = "donThuocs", ignore = true)
    @Mapping(target = "baoCaoPhanUngs", ignore = true)
    @Mapping(target = "phanHois", ignore = true)
    @Mapping(target = "bangGiaVacXins", ignore = true)
    @Mapping(target = "ketQuaTiemsThucHien", ignore = true)
    @Mapping(target = "dichBenhs", ignore = true)
    @Mapping(target = "description", source = "description")
    TaiKhoanEntity toEntity(TaiKhoanCreateRequest request);

}