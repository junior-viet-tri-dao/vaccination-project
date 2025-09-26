package com.viettridao.vaccination.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiCapCaoRequest;
import com.viettridao.vaccination.model.PhanHoiEntity;

@Mapper(componentModel = "spring")
public interface PhanHoiCapCaoMapper {

    PhanHoiCapCaoMapper INSTANCE = Mappers.getMapper(PhanHoiCapCaoMapper.class);

    /**
     * Chuyển PhanHoiCapCaoRequest thành PhanHoiEntity (chỉ map các trường cần thiết).
     * Các trường như benhNhan, taoBoi, ngayTao sẽ set ở service/controller.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "benhNhan", ignore = true)
    @Mapping(target = "taoBoi", ignore = true)
    @Mapping(target = "ngayTao", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "trangThai", ignore = true)
    @Mapping(target = "tieuDe", ignore = true)
    PhanHoiEntity toEntity(PhanHoiCapCaoRequest request);

    // Map từ Entity → DTO cho admin dashboard
    @Mapping(target = "tenBenhNhan", source = "benhNhan.hoTen")
    com.viettridao.vaccination.dto.response.PhanHoiAdminResponse toAdminResponse(PhanHoiEntity entity);

    List<com.viettridao.vaccination.dto.response.PhanHoiAdminResponse> toAdminResponseList(List<PhanHoiEntity> entities);
}