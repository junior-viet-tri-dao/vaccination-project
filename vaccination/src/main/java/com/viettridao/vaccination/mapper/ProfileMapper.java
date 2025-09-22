package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.normalUser.EditProfileRequest;
import com.viettridao.vaccination.dto.response.normalUser.LichSuTiemResponse;
import com.viettridao.vaccination.dto.response.normalUser.ProfileDetailResponse;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    // Map thông tin cá nhân (BenhNhanEntity sang ProfileDetailResponse, không bao gồm lịch sử tiêm)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ten", source = "hoTen")
    @Mapping(target = "gioiTinh", source = "gioiTinh", qualifiedByName = "gioiTinhToString")
    @Mapping(target = "diaChi", source = "diaChi")
    @Mapping(target = "ngaySinh", source = "ngaySinh")
    @Mapping(target = "lichSuTiem", ignore = true)
    // sẽ set sau
    ProfileDetailResponse toProfileDetail(BenhNhanEntity entity);

    // Map danh sách KetQuaTiemEntity sang danh sách LichSuTiemResponse, tự động thêm STT
    default List<LichSuTiemResponse> toLichSuTiemList(List<KetQuaTiemEntity> entities) {
        List<LichSuTiemResponse> list = entities.stream()
                .map(this::toLichSuTiemResponse)
                .toList();
        IntStream.range(0, list.size()).forEach(i -> list.get(i).setStt(i + 1));
        return list;
    }

    // Map 1 lịch sử tiêm
    @Mapping(target = "thoiGian", source = "lichTiem.ngayGio")
    @Mapping(target = "diaDiem", source = "lichTiem.diaDiem")
    @Mapping(target = "tenVacXin", source = "lichTiem.vacXin.ten")
    @Mapping(target = "loaiVacXin", source = "lichTiem.vacXin.loai")
    @Mapping(target = "lieuLuong", expression = "java(\"1 liều\")") // Nếu dữ liệu khác thì sửa lại
    @Mapping(target = "nguoiTiem", source = "nguoiThucHien.hoTen")
    @Mapping(target = "ketQua", source = "phanUngSauTiem") // Lấy kết quả từ phản ứng sau tiêm
    @Mapping(target = "ghiChu", source = "ghiChu")
    @Mapping(target = "stt", ignore = true)
    // sẽ set ở trên
    LichSuTiemResponse toLichSuTiemResponse(KetQuaTiemEntity entity);

    // Map enum giới tính sang string
    @Named("gioiTinhToString")
    default String gioiTinhToString(BenhNhanEntity.GioiTinh gioiTinh) {
        return gioiTinh == null ? "" : (gioiTinh == BenhNhanEntity.GioiTinh.NAM ? "Nam" : "Nữ");
    }

    @Mapper(componentModel = "spring")
    public interface BenhNhanMapper {
        EditProfileRequest toEditProfileRequest(BenhNhanEntity entity);

        @Mapping(target = "id", ignore = true)
            // Không cho sửa id qua request
        void updateBenhNhanFromRequest(EditProfileRequest request, @MappingTarget BenhNhanEntity entity);
    }
}