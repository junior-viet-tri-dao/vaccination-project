package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiCapCaoRequest;
import com.viettridao.vaccination.model.PhanHoiEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
    com.viettridao.vaccination.dto.response.feedback.PhanHoiAdminResponse toAdminResponse(PhanHoiEntity entity);

    List<com.viettridao.vaccination.dto.response.feedback.PhanHoiAdminResponse> toAdminResponseList(List<PhanHoiEntity> entities);
}