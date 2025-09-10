package com.viettridao.vaccination.service;


import org.springframework.data.domain.Page;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;

public interface VaccineService {
    Page<VaccinePriceResponse> getAllVaccinePrices(int page, int size);
}

