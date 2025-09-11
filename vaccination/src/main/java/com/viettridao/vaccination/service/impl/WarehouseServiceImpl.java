package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.request.warehouse.ExportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.mapper.WarehouseMapper;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.repository.WarehouseRepository;
import com.viettridao.vaccination.repository.LoVacXinRepository;
import com.viettridao.vaccination.service.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final VacXinRepository vaccineRepository;
    private final LoVacXinRepository loVacXinRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    @Transactional
    public ImportResponse importVaccine(ImportRequest request) {
        // Kiểm tra trùng mã lô
        warehouseRepository.findByMaLoCodeIgnoreCaseAndIsDeletedFalse(request.getMaLoCode())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Mã lô '" + request.getMaLoCode() + "' đã tồn tại!");
                });

        // Lấy hoặc tạo VacXinEntity
        VacXinEntity vacXin = vaccineRepository.findByTenAndIsDeletedFalse(request.getTenVacXin())
                .orElseGet(() -> {
                    VacXinEntity newVacXin = warehouseMapper.toVacXinEntity(request);
                    newVacXin.setIsDeleted(Boolean.FALSE);
                    return vaccineRepository.save(newVacXin);
                });

        // Tạo LoVacXinEntity
        LoVacXinEntity loVacXin = warehouseMapper.toLoVacXinEntity(request, vacXin);
        loVacXin.setIsDeleted(Boolean.FALSE);
        LoVacXinEntity saved = loVacXinRepository.save(loVacXin);

        return warehouseMapper.toImportResponse(saved);
    }

    @Override
    @Transactional
    public WarehouseResponse exportVaccine(ExportRequest request) {
        LoVacXinEntity loVacXin = warehouseRepository.findByMaLoCodeIgnoreCaseAndIsDeletedFalse(request.getMaLoCode())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lô vắc-xin"));
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải > 0");
        }
        if (request.getQuantity() > loVacXin.getSoLuong()) {
            throw new IllegalArgumentException("Số lượng xuất vượt quá số lượng tồn kho");
        }

        int remaining = loVacXin.getSoLuong() - request.getQuantity();
        loVacXin.setSoLuong(remaining);

        // Cập nhật tình trạng vắc xin
        if (remaining == 0) {
            loVacXin.setTinhTrang(LoVacXinEntity.TinhTrang.HET);
        } else {
            loVacXin.setTinhTrang(LoVacXinEntity.TinhTrang.CO);
        }

        loVacXinRepository.save(loVacXin);

        return warehouseMapper.toWarehouseResponse(loVacXin);
    }

    @Override
    public Page<WarehouseResponse> getWarehouses(String searchType, String keyword, int pageNo, int pageSize) {
        if (pageNo < 0) pageNo = 0;
        Page<WarehouseResponse> warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);

        if (pageNo >= warehousePage.getTotalPages() && warehousePage.getTotalPages() > 0) {
            pageNo = warehousePage.getTotalPages() - 1;
            warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);
        }

        List<WarehouseResponse> content = warehousePage.getContent().stream()
                .map(item -> {
                    if ("CO".equalsIgnoreCase(item.getTinhTrangVacXin())) {
                        item.setTinhTrangVacXin("Có");
                    } else if ("HET".equalsIgnoreCase(item.getTinhTrangVacXin())) {
                        item.setTinhTrangVacXin("Hết");
                    }
                    return item;
                })
                .toList();

        return new PageImpl<>(content, warehousePage.getPageable(), warehousePage.getTotalElements());
    }

    private Page<WarehouseResponse> fetchPage(String searchType, String keyword, int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<LoVacXinEntity> entities;

        if (keyword == null || keyword.trim().isEmpty()) {
            entities = warehouseRepository.findAllNotDeleted(pageable);
        } else {
            switch (searchType) {
                case "name":
                    entities = warehouseRepository.findByTenVacXinNotDeleted(keyword.trim(), pageable);
                    break;
                case "type":
                    entities = warehouseRepository.findByLoaiVacXinNotDeleted(keyword.trim(), pageable);
                    break;
                case "origin":
                    entities = warehouseRepository.findByNuocSanXuatContainingIgnoreCaseAndIsDeletedFalse(keyword.trim(), pageable);
                    break;
                case "age":
                    entities = warehouseRepository.findByDoiTuongTiemNotDeleted(keyword.trim(), pageable);
                    break;
                default:
                    entities = warehouseRepository.findAllNotDeleted(pageable);
                    break;
            }
        }

        return entities.map(warehouseMapper::toWarehouseResponse);
    }
}