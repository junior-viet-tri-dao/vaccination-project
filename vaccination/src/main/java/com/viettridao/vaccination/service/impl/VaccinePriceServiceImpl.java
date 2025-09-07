package com.viettridao.vaccination.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.mapper.VaccinePriceMapper;
import com.viettridao.vaccination.model.VaccineBatchEntity;
import com.viettridao.vaccination.repository.VaccineBatchRepository;
import com.viettridao.vaccination.service.VaccinePriceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccinePriceServiceImpl implements VaccinePriceService {
    private final VaccineBatchRepository batchRepository;
    private final VaccinePriceMapper mapper;

    @Override
    public Page<VaccinePriceResponse> getPagedBatches(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productionYear").descending());
        Page<VaccineBatchEntity> entityPage = batchRepository.findAll(pageable);

        return entityPage.map(mapper::toResponse);
    }
}
