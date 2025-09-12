package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.mapper.VaccineListMapper;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.VaccineListService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineListServiceImpl implements VaccineListService {

    private final VacXinRepository vacXinRepository;
    private final VaccineListMapper vaccineListMapper;

    public VaccineListServiceImpl(VacXinRepository vacXinRepository, VaccineListMapper vaccineListMapper) {
        this.vacXinRepository = vacXinRepository;
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
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("ten").ascending());
        Page<VacXinEntity> entityPage;

        if (keyword == null || keyword.trim().isEmpty()) {
            entityPage = vacXinRepository.findAll(pageable);
        } else {
            switch (searchType) {
                case "maVacXin":
                    entityPage = vacXinRepository.findByMaCodeContainingIgnoreCaseAndIsDeletedFalse(keyword, pageable);
                    break;
                case "tenVacXin":
                    entityPage = vacXinRepository.findByTenContainingIgnoreCaseAndIsDeletedFalse(keyword, pageable);
                    break;
                case "phongTriBenh":
                    entityPage = vacXinRepository.findByMoTaContainingIgnoreCaseAndIsDeletedFalse(keyword, pageable);
                    break;
                default:
                    entityPage = vacXinRepository.findAll(pageable);
            }
        }

        List<VaccineListResponse> dtoList = entityPage.getContent().stream()
                .map(vaccineListMapper::toVaccineListResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}