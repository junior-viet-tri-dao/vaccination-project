package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VaccineListMapper {

    @Mapping(target = "maVacXin", source = "vacXin.maCode")
    @Mapping(target = "tenVacXin", source = "vacXin.ten")
    @Mapping(target = "phongTriBenh", source = "vacXin.moTa")
    @Mapping(target = "soLuong", source = "vacXin", qualifiedByName = "calculateTotalQuantity")
    @Mapping(target = "doTuoiTiemPhong", source = "vacXin.doiTuongTiem")
    VaccineListResponse toVaccineListResponse(BangGiaVacXinEntity bangGiaVacXin);

    @Named("calculateTotalQuantity")
    static int calculateTotalQuantity(VacXinEntity vacXinEntity) {
        if (vacXinEntity == null || vacXinEntity.getLoVacXins() == null) {
            return 0;
        }
        return vacXinEntity.getLoVacXins().stream()
                .filter(lo -> lo.getIsDeleted() == null || !lo.getIsDeleted())
                .mapToInt(lo -> lo.getSoLuong() == null ? 0 : lo.getSoLuong())
                .sum();
    }
}