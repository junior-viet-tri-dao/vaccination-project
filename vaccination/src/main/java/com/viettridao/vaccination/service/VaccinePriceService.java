package com.viettridao.vaccination.service;

import org.springframework.data.domain.Page;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;

public interface VaccinePriceService {
    Page<VaccinePriceResponse> getPagedBatches(int page, int size);

}
