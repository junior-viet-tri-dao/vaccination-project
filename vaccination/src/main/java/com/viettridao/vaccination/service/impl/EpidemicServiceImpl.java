package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.response.EpidemicResponse;
import com.viettridao.vaccination.mapper.EpidemicMapper;
import com.viettridao.vaccination.model.EpidemicEntity;
import com.viettridao.vaccination.repository.EpidemicRepository;
import com.viettridao.vaccination.service.EpidemicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpidemicServiceImpl implements EpidemicService {

    private final EpidemicRepository epidemicRepository;
    private final EpidemicMapper epidemicMapper;

    @Override
    public Page<EpidemicResponse> getAllEpidemics(int page, int size) {
        // ✅ Validate đầu vào
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "surveyTime"));
        Page<EpidemicEntity> entityPage = epidemicRepository.findAll(pageable);

        // ✅ Nếu vượt quá số trang thì load lại trang cuối
        if (entityPage.getTotalPages() > 0 && page >= entityPage.getTotalPages()) {
            page = entityPage.getTotalPages() - 1;
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "surveyTime"));
            entityPage = epidemicRepository.findAll(pageable);
        }

        // ✅ Map Entity → DTO + set STT
        int currentPage = page; // lưu lại page sau khi chỉnh sửa
        Page<EpidemicEntity> finalEntityPage = entityPage;
        int finalSize = size;
        return entityPage.map(entity -> {
            EpidemicResponse dto = epidemicMapper.toResponse(entity);

            // Công thức: index = vị trí trong page + offset + 1
            int rowIndex = finalEntityPage.getContent().indexOf(entity) + 1 + (currentPage * finalSize);
            dto.setIndex(rowIndex);

            return dto;
        });
    }
}