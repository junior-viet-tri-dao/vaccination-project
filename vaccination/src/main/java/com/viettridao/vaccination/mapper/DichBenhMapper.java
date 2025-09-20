package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.response.DichBenhResponse;
import com.viettridao.vaccination.model.DichBenhEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper sử dụng MapStruct để chuyển đổi DichBenhEntity sang DichBenhResponse.
 */
@Mapper(componentModel = "spring")
public interface DichBenhMapper {

    // Map từ Entity sang Response, stt sẽ set riêng ở service khi trả danh sách
    @Mappings({
            @Mapping(target = "stt", ignore = true) // STT sẽ được set ở tầng service/controller
    })
    DichBenhResponse toResponse(DichBenhEntity entity);

    List<DichBenhResponse> toResponseList(List<DichBenhEntity> entities);
}