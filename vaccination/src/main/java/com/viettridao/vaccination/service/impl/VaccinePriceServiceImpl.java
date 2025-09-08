package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.finance.UpdateVaccinePriceRequest;
import com.viettridao.vaccination.model.VaccineEntity;
import jakarta.transaction.Transactional;
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
        Page<VaccineBatchEntity> entityPage = batchRepository.findAllActive(pageable);

        return entityPage.map(mapper::toResponse);
    }

    @Override
    public VaccinePriceResponse getById(String batchId) {
        VaccineBatchEntity entity = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        return mapper.toResponse(entity);
    }
    
    
    @Override
    public UpdateVaccinePriceRequest getByBatchId(String batchId) {
        VaccineBatchEntity entity = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        UpdateVaccinePriceRequest request = new UpdateVaccinePriceRequest();
        request.setBatchId(entity.getBatchId());

        if (entity.getVaccine() != null) {
            request.setVaccineCode(entity.getVaccine().getVaccineCode());
            request.setUnit(entity.getVaccine().getUnit());
            request.setUnitPrice(entity.getVaccine().getUnitPrice());
        }

        request.setProductionYear(entity.getProductionYear());
        return request;
    }


    @Override
    @Transactional
    public void update(String batchId, UpdateVaccinePriceRequest request) {
        VaccineBatchEntity entity = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        // Update thông tin
        entity.setProductionYear(request.getProductionYear());

        // Update vaccine info
        if (entity.getVaccine() != null) {
            entity.getVaccine().setVaccineCode(request.getVaccineCode());
            entity.getVaccine().setUnit(request.getUnit());
            entity.getVaccine().setUnitPrice(request.getUnitPrice());
        } else {
            // Trường hợp chưa có vaccine (an toàn)
            VaccineEntity vaccine = new VaccineEntity();
            vaccine.setVaccineCode(request.getVaccineCode());
            vaccine.setUnit(request.getUnit());
            vaccine.setUnitPrice(request.getUnitPrice());
            entity.setVaccine(vaccine);
        }

        batchRepository.save(entity);
    }

    @Override
    @Transactional
    public void softDeleteBatch(String batchId) {
        VaccineBatchEntity entity = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        entity.setIsDeleted(true);
        batchRepository.save(entity);
    }

}
