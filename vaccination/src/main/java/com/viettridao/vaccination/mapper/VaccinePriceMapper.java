package com.viettridao.vaccination.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Mapper(componentModel = "spring")
public interface VaccinePriceMapper {

    VaccinePriceMapper INSTANCE = Mappers.getMapper(VaccinePriceMapper.class);

    @Mapping(source = "vacXin.id", target = "vaccineId")
    @Mapping(source = "vacXin.maCode", target = "maCode")
    @Mapping(source = "vacXin.ten", target = "ten")
    @Mapping(source = "loVacXin.donVi", target = "donVi")
    @Mapping(expression = "java(loVacXin.getNgaySanXuat() != null ? loVacXin.getNgaySanXuat().getYear() : null)", 
             target = "namSanXuat")
    @Mapping(source = "bangGia.gia", target = "gia")
    VaccinePriceResponse toDto(VacXinEntity vacXin,
                               LoVacXinEntity loVacXin,
                               BangGiaVacXinEntity bangGia);
}
