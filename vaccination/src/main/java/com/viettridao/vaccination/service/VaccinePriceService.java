package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.finance.UpdateVaccinePriceRequest;
import org.springframework.data.domain.Page;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;

public interface VaccinePriceService {
    Page<VaccinePriceResponse> getPagedBatches(int page, int size);

    public void softDeleteBatch(String batchId);

    void update(String batchId, UpdateVaccinePriceRequest request);

    VaccinePriceResponse getById(String batchId);
}
