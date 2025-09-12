package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.model.VacXinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VaccineListMapper {

    @Mapping(target = "maVacXin", source = "maCode")
    @Mapping(target = "tenVacXin", source = "ten")
    @Mapping(target = "phongTriBenh", source = "moTa")
    @Mapping(target = "soLuong", source = ".", qualifiedByName = "calculateTotalQuantity")
    @Mapping(target = "doTuoiTiemPhong", source = "doiTuongTiem")
    VaccineListResponse toVaccineListResponse(VacXinEntity vacXinEntity);

    @Named("calculateTotalQuantity")
    static int calculateTotalQuantity(VacXinEntity vacXinEntity) {
        if (vacXinEntity.getLoVacXins() == null) {
            return 0;
        }
        return vacXinEntity.getLoVacXins().stream()
                .filter(lo -> lo.getIsDeleted() != null && !lo.getIsDeleted())
                .mapToInt(lo -> lo.getSoLuong() == null ? 0 : lo.getSoLuong())
                .sum();
    }
}