package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.response.EpidemicResponse;
import com.viettridao.vaccination.model.EpidemicEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * EpidemicMapper
 * Dùng MapStruct để convert giữa EpidemicEntity và EpidemicResponse.
 */
@Mapper(componentModel = "spring")
public interface EpidemicMapper {

    /**
     * Convert từ Entity sang Response DTO
     *
     * @param entity EpidemicEntity
     * @return EpidemicResponse
     */
    @Mapping(target = "index", ignore = true) // index sẽ set thủ công ở Service
    EpidemicResponse toResponse(EpidemicEntity entity);

    /**
     * Convert list Entity sang list Response DTO
     *
     * @param entities danh sách EpidemicEntity
     * @return danh sách EpidemicResponse
     */
    List<EpidemicResponse> toResponseList(List<EpidemicEntity> entities);
}