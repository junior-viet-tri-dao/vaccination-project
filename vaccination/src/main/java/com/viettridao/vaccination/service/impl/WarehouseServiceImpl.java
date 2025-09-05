package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.mapper.WarehouseMapper;
import com.viettridao.vaccination.model.VaccineBatchEntity;
import com.viettridao.vaccination.model.VaccineEntity;
import com.viettridao.vaccination.model.VaccineTypeEntity;
import com.viettridao.vaccination.repository.VaccineTypeRepository;
import com.viettridao.vaccination.repository.WarehouseRepository;
import com.viettridao.vaccination.service.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final VaccineTypeRepository vaccineTypeRepository;

    public List<String> getAllVaccineTypeNames() {
        return vaccineTypeRepository.findAll()
                .stream()
                .map(VaccineTypeEntity::getVaccineTypeName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ImportResponse importVaccine(ImportRequest request) {
        // 1️⃣ Tạo VaccineTypeEntity (nested object)
        VaccineTypeEntity type = new VaccineTypeEntity();
        type.setVaccineTypeName(request.getVaccineType());
        type.setIsDeleted(false);

        // 2️⃣ Tạo VaccineEntity từ request
        VaccineEntity vaccine = warehouseMapper.toVaccineEntity(request);
        vaccine.setVaccineType(type); // liên kết VaccineType

        // 3️⃣ Tạo VaccineBatchEntity từ request + VaccineEntity
        VaccineBatchEntity batch = warehouseMapper.toVaccineBatchEntity(request, vaccine);

        // 4️⃣ Lưu vào DB (cascade sẽ lưu vaccine + type nếu cascade được bật)
        warehouseRepository.save(batch);

        // 5️⃣ Map lại sang ImportResponse để trả về
        return warehouseMapper.toImportResponse(batch);
    }

    @Override
    public Page<WarehouseResponse> getWarehouses(String searchType, String keyword, int pageNo, int pageSize) {
        // Fix page ngoài phạm vi
        if (pageNo < 0) {
            pageNo = 0;
        }

        Page<WarehouseResponse> warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);

        // Nếu page ngoài tổng số trang → chỉnh lại
        if (pageNo >= warehousePage.getTotalPages() && warehousePage.getTotalPages() > 0) {
            pageNo = warehousePage.getTotalPages() - 1;
            warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);
        }

        return warehousePage;
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