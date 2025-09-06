package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.common.WarehouseStatus;
import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.mapper.WarehouseMapper;
import com.viettridao.vaccination.model.VaccineBatchEntity;
import com.viettridao.vaccination.model.VaccineEntity;
import com.viettridao.vaccination.model.VaccineTypeEntity;
import com.viettridao.vaccination.repository.VaccineRepository;
import com.viettridao.vaccination.repository.VaccineTypeRepository;
import com.viettridao.vaccination.repository.WarehouseRepository;
import com.viettridao.vaccination.service.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final VaccineTypeRepository vaccineTypeRepository;
    private final VaccineRepository vaccineRepository;

    @Override
    @Transactional
    public ImportResponse importVaccine(ImportRequest request) {
        // 1️⃣ Lấy hoặc tạo VaccineTypeEntity
        VaccineTypeEntity type = vaccineTypeRepository
                .findByVaccineTypeNameIgnoreCase(request.getVaccineType())
                .orElseGet(() -> {
                    VaccineTypeEntity newType = new VaccineTypeEntity();
                    newType.setVaccineTypeName(request.getVaccineType());
                    newType.setIsDeleted(false);
                    return vaccineTypeRepository.save(newType);
                });

        // 2️⃣ Lấy hoặc tạo VaccineEntity
        VaccineEntity vaccine = warehouseRepository.findVaccineByNameAndType(
                        request.getVaccineName(), type.getVaccineTypeName())
                .orElseGet(() -> {
                    VaccineEntity newVaccine = warehouseMapper.toVaccineEntity(request);
                    newVaccine.setVaccineType(type);

                    // 🔹 Giá trị mặc định
                    newVaccine.setPreventDisease("Unknown");
                    newVaccine.setIsDeleted(false);

                    return newVaccine;
                });

        // 🚨 Nếu vaccine mới tạo (chưa có id) → save trước
        if (vaccine.getVaccineId() == null) {
            vaccine = vaccineRepository.save(vaccine);
        }

        // 3️⃣ Tạo VaccineBatchEntity từ request + VaccineEntity
        VaccineBatchEntity batch = warehouseMapper.toVaccineBatchEntity(request, vaccine);
        batch.setVaccine(vaccine);

        // 🔹 Set giá trị mặc định cho status
        batch.setStatus("AVAILABLE");
        batch.setIsDeleted(false);

        // 4️⃣ Save batch
        VaccineBatchEntity saved = warehouseRepository.save(batch);

        // 5️⃣ Trả về response
        return warehouseMapper.toImportResponse(saved);
    }

    @Override
    @Transactional
    public WarehouseResponse exportVaccine(String batchId, int quantity) {
        VaccineBatchEntity batch = warehouseRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lô vắc-xin"));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải > 0");
        }
        if (quantity > batch.getQuantity()) {
            throw new IllegalArgumentException("Số lượng xuất vượt quá số lượng tồn kho");
        }

        int remaining = batch.getQuantity() - quantity;
        batch.setQuantity(remaining);

        if (remaining == 0) {
            batch.setStatus(WarehouseStatus.OUT_OF_STOCK.name());
        } else {
            batch.setStatus(WarehouseStatus.AVAILABLE.name());
        }

        warehouseRepository.save(batch);

        return warehouseMapper.toResponse(batch);
    }

    @Override
    public Page<WarehouseResponse> getWarehouses(String searchType, String keyword, int pageNo, int pageSize) {
        // Fix page ngoài phạm vi
        if (pageNo < 0) {
            pageNo = 0;
        }

        // Lấy page từ database
        Page<WarehouseResponse> warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);

        // Nếu page ngoài tổng số trang → chỉnh lại
        if (pageNo >= warehousePage.getTotalPages() && warehousePage.getTotalPages() > 0) {
            pageNo = warehousePage.getTotalPages() - 1;
            warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);
        }

        // Map status sang tiếng Việt
        List<WarehouseResponse> content = warehousePage.getContent().stream()
                .map(item -> {
                    if ("AVAILABLE".equalsIgnoreCase(item.getStatus())) {
                        item.setStatus("Có");
                    } else if ("OUT_OF_STOCK".equalsIgnoreCase(item.getStatus())) {
                        item.setStatus("Hết");
                    }
                    return item;
                })
                .toList();

        // Trả về Page mới với content đã map
        return new PageImpl<>(content, warehousePage.getPageable(), warehousePage.getTotalElements());
    }

    private Page<WarehouseResponse> fetchPage(String searchType, String keyword, int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<VaccineBatchEntity> entities;

        if (keyword == null || keyword.trim().isEmpty()) {
            entities = warehouseRepository.findAll(pageable);
        } else {
            switch (searchType) {
                case "name":
                    entities = warehouseRepository.findByVaccine_VaccineNameContainingIgnoreCase(keyword, pageable);
                    break;
                case "type":
                    entities = warehouseRepository.findByVaccine_VaccineType_VaccineTypeNameContainingIgnoreCase(keyword, pageable);
                    break;
                case "origin":
                    entities = warehouseRepository.findByCountryOfOriginContainingIgnoreCase(keyword, pageable);
                    break;
                case "age":
                    entities = warehouseRepository.findByVaccine_AgeGroupContainingIgnoreCase(keyword, pageable);
                    break;
                default:
                    entities = warehouseRepository.findAll(pageable);
                    break;
            }
        }

        return entities.map(warehouseMapper::toResponse);
    }
}