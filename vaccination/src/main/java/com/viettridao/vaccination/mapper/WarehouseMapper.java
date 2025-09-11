package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    // Map ImportRequest to VacXinEntity
    @Mapping(target = "ten", source = "tenVacXin")
    @Mapping(target = "loai", source = "loaiVacXin")
    @Mapping(target = "doiTuongTiem", source = "doiTuongTiem")
    @Mapping(target = "moTa", ignore = true)
    @Mapping(target = "maCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    VacXinEntity toVacXinEntity(ImportRequest request);

    // Map ImportRequest to LoVacXinEntity
    @Mapping(target = "vacXin", source = "vacXinEntity")
    @Mapping(target = "maLoCode", source = "request.maLoCode")
    @Mapping(target = "nuocSanXuat", source = "request.nuocSanXuat")
    @Mapping(target = "hamLuong", source = "request.hamLuong")
    @Mapping(target = "hanSuDung", source = "request.hanSuDung")
    @Mapping(target = "soLuong", source = "request.soLuong")
    @Mapping(target = "donGia", source = "request.donGia")
    @Mapping(target = "dieuKienBaoQuan", source = "request.dieuKienBaoQuan")
    @Mapping(target = "soGiayPhep", source = "request.soGiayPhep")
    @Mapping(target = "ngayNhap", source = "request.ngayNhap")
    @Mapping(target = "tinhTrang", constant = "CO")
    @Mapping(target = "id", ignore = true)
    LoVacXinEntity toLoVacXinEntity(ImportRequest request, VacXinEntity vacXinEntity);

    // Map LoVacXinEntity to ImportResponse
    @Mapping(target = "tenVacXin", source = "vacXin.ten")
    @Mapping(target = "loaiVacXin", source = "vacXin.loai")
    @Mapping(target = "doiTuongTiem", source = "vacXin.doiTuongTiem")
    @Mapping(target = "maLoCode", source = "maLoCode")
    @Mapping(target = "ngayNhap", source = "ngayNhap")
    @Mapping(target = "soGiayPhep", source = "soGiayPhep")
    @Mapping(target = "nuocSanXuat", source = "nuocSanXuat")
    @Mapping(target = "hamLuong", source = "hamLuong")
    @Mapping(target = "soLuong", source = "soLuong")
    @Mapping(target = "donGia", source = "donGia")
    @Mapping(target = "hanSuDung", source = "hanSuDung")
    @Mapping(target = "dieuKienBaoQuan", source = "dieuKienBaoQuan")
    ImportResponse toImportResponse(LoVacXinEntity entity);

    // Map ImportRequest to ImportResponse (bổ sung)
    @Mapping(target = "tenVacXin", source = "tenVacXin")
    @Mapping(target = "loaiVacXin", source = "loaiVacXin")
    @Mapping(target = "doiTuongTiem", source = "doiTuongTiem")
    @Mapping(target = "maLoCode", source = "maLoCode")
    @Mapping(target = "ngayNhap", source = "ngayNhap")
    @Mapping(target = "soGiayPhep", source = "soGiayPhep")
    @Mapping(target = "nuocSanXuat", source = "nuocSanXuat")
    @Mapping(target = "hamLuong", source = "hamLuong")
    @Mapping(target = "soLuong", source = "soLuong")
    @Mapping(target = "donGia", source = "donGia")
    @Mapping(target = "hanSuDung", source = "hanSuDung")
    @Mapping(target = "dieuKienBaoQuan", source = "dieuKienBaoQuan")
    ImportResponse requestToImportResponse(ImportRequest request);

    // Map LoVacXinEntity to WarehouseResponse
    @Mapping(target = "tenVacXin", source = "vacXin.ten")
    @Mapping(target = "loaiVacXin", source = "vacXin.loai")
    @Mapping(target = "doiTuongTiem", source = "vacXin.doiTuongTiem")
    @Mapping(target = "maLoCode", source = "maLoCode")
    @Mapping(target = "ngayNhap", source = "ngayNhap")
    @Mapping(target = "soGiayPhep", source = "soGiayPhep")
    @Mapping(target = "nuocSanXuat", source = "nuocSanXuat")
    @Mapping(target = "hamLuong", source = "hamLuong")
    @Mapping(target = "soLuong", source = "soLuong")
    @Mapping(target = "hanSuDung", source = "hanSuDung")
    @Mapping(target = "dieuKienBaoQuan", source = "dieuKienBaoQuan")
    @Mapping(target = "tinhTrangVacXin", expression = "java(entity.getTinhTrang().name())")
    WarehouseResponse toWarehouseResponse(LoVacXinEntity entity);
}
