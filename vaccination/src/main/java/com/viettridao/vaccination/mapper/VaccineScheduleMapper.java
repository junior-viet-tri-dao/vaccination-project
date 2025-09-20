package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.response.normalUser.VaccineScheduleResponse;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Mapper cho DTO VaccineScheduleResponse từ entity LichTiemEntity.
 */
@Mapper(componentModel = "spring")
public interface VaccineScheduleMapper {

    /**
     * Map một LichTiemEntity sang VaccineScheduleResponse.
     * - Lấy giá vắc xin (ghi chú) từ BangGiaVacXinEntity còn hiệu lực hoặc gần nhất.
     */
    @Mapping(target = "ngayTiem", source = "ngayGio", qualifiedByName = "toLocalDate")
    @Mapping(target = "thoiGian", source = "ngayGio", qualifiedByName = "toLocalTime")
    @Mapping(target = "diaDiem", source = "diaDiem")
    @Mapping(target = "tenVacXin", source = "vacXin.ten")
    @Mapping(target = "loaiVacXin", source = "vacXin.loai")
    @Mapping(target = "soLuong", source = "sucChua", defaultValue = "0")
    @Mapping(target = "doiTuong", source = "vacXin.doiTuongTiem")
    @Mapping(target = "ghiChu", source = ".", qualifiedByName = "getGhiChuFromBangGia")
    VaccineScheduleResponse toVaccineScheduleResponse(LichTiemEntity lichTiem);

    // Map LocalDateTime -> LocalDate
    @Named("toLocalDate")
    static LocalDate toLocalDate(java.time.LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }

    // Map LocalDateTime -> LocalTime
    @Named("toLocalTime")
    static LocalTime toLocalTime(java.time.LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalTime() : null;
    }

    /**
     * Lấy ghi chú giá vắc xin từ bảng giá (lấy giá còn hiệu lực hoặc gần nhất).
     * - Nếu miễn phí, trả về "Miễn phí"
     * - Nếu có giá, trả về giá dạng "120.000đ/mũi"
     */
    @Named("getGhiChuFromBangGia")
    static String getGhiChuFromBangGia(LichTiemEntity lichTiem) {
        VacXinEntity vacXin = lichTiem.getVacXin();
        if (vacXin == null || vacXin.getBangGiaVacXins() == null || vacXin.getBangGiaVacXins().isEmpty()) {
            return "";
        }
        LocalDate ngayTiem = lichTiem.getNgayGio() != null ? lichTiem.getNgayGio().toLocalDate() : LocalDate.now();
        // Tìm giá còn hiệu lực tại ngày lịch tiêm (ưu tiên giá mới nhất)
        Optional<BangGiaVacXinEntity> giaHienTai = vacXin.getBangGiaVacXins().stream()
                .filter(bg -> (bg.getHieuLucTu() == null || !ngayTiem.isBefore(bg.getHieuLucTu()))
                        && (bg.getHieuLucDen() == null || !ngayTiem.isAfter(bg.getHieuLucDen())))
                .sorted((a, b) -> {
                    LocalDate aTu = a.getHieuLucTu() == null ? LocalDate.MIN : a.getHieuLucTu();
                    LocalDate bTu = b.getHieuLucTu() == null ? LocalDate.MIN : b.getHieuLucTu();
                    return bTu.compareTo(aTu); // giảm dần, lấy mới nhất
                })
                .findFirst();

        if (giaHienTai.isPresent()) {
            Integer gia = giaHienTai.get().getGia();
            if (gia == null || gia == 0) return "Miễn phí";
            // Hiển thị giá dạng "120.000đ/mũi"
            return String.format("%,d đ/mũi", gia);
        }
        return "";
    }
}