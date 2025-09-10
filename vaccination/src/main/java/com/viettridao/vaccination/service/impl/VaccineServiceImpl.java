package com.viettridao.vaccination.service.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.response.finance.VaccinePriceResponse;
import com.viettridao.vaccination.mapper.VaccinePriceMapper;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.repository.BangGiaVacXinRepository;
import com.viettridao.vaccination.repository.LoVacXinRepository;
import com.viettridao.vaccination.service.VaccineService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccineServiceImpl implements VaccineService {

    private final LoVacXinRepository loVacXinRepository;
    private final BangGiaVacXinRepository bangGiaVacXinRepository;
    private final VaccinePriceMapper vaccinePriceMapper;

    @Override
    public Page<VaccinePriceResponse> getAllVaccinePrices(int page, int size) {
        LocalDate today = LocalDate.now();

        // Phân trang lô vắc xin
        Page<LoVacXinEntity> loVacXinPage = loVacXinRepository.findAll(PageRequest.of(page, size));

        // Map từng lô -> DTO kèm giá hiện tại
        return loVacXinPage.map(lo -> {
            // Lấy giá hiện tại (lấy bản ghi mới nhất, phân trang size=1)
            Page<BangGiaVacXinEntity> bangGiaPage = bangGiaVacXinRepository.findGiaHienTai(
                    lo.getVacXin().getId(),
                    today,
                    PageRequest.of(0, 1)
            );

            BangGiaVacXinEntity gia = bangGiaPage.isEmpty() ? null : bangGiaPage.getContent().get(0);

            return vaccinePriceMapper.toDto(lo.getVacXin(), lo, gia);
        });
    }
}

