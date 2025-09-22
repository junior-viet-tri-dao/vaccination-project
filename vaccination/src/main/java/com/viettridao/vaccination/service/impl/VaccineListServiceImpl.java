package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.mapper.VaccineListMapper;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.repository.BangGiaVacXinRepository;
import com.viettridao.vaccination.service.VaccineListService;

@Service
public class VaccineListServiceImpl implements VaccineListService {

    private final BangGiaVacXinRepository bangGiaVacXinRepository;
    private final VaccineListMapper vaccineListMapper;

    public VaccineListServiceImpl(BangGiaVacXinRepository bangGiaVacXinRepository, VaccineListMapper vaccineListMapper) {
        this.bangGiaVacXinRepository = bangGiaVacXinRepository;
        this.vaccineListMapper = vaccineListMapper;
    }

    @Override
    public Page<VaccineListResponse> getVaccines(String searchType, String keyword, int pageNo, int pageSize) {
        if (pageNo < 0) pageNo = 0;

        Page<VaccineListResponse> vaccinePage = fetchPage(searchType, keyword, pageNo, pageSize);

        if (pageNo >= vaccinePage.getTotalPages() && vaccinePage.getTotalPages() > 0) {
            pageNo = vaccinePage.getTotalPages() - 1;
            vaccinePage = fetchPage(searchType, keyword, pageNo, pageSize);
        }

        return vaccinePage;
    }

    private Page<VaccineListResponse> fetchPage(String searchType, String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<BangGiaVacXinEntity> entityPage;
        // Tìm kiếm theo các trường liên quan đến bảng giá vắc xin hoặc vắc xin
        if (keyword == null || keyword.trim().isEmpty()) {
            entityPage = bangGiaVacXinRepository.findAllByIsDeletedFalse(pageable);
        } else {
            switch (searchType) {
                case "maVacXin":
                    entityPage = bangGiaVacXinRepository.findByVacXin_MaCodeContainingIgnoreCaseAndIsDeletedFalse(keyword, pageable);
                    break;
                case "tenVacXin":
                    entityPage = bangGiaVacXinRepository.findByVacXin_TenContainingIgnoreCaseAndIsDeletedFalse(keyword, pageable);
                    break;
                case "phongTriBenh":
                    entityPage = bangGiaVacXinRepository.findByVacXin_MoTaContainingIgnoreCaseAndIsDeletedFalse(keyword, pageable);
                    break;
                default:
                    entityPage = bangGiaVacXinRepository.findAllByIsDeletedFalse(pageable);
            }
        }

        List<VaccineListResponse> dtoList = entityPage.getContent().stream()
                .map(vaccineListMapper::toVaccineListResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}