package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.model.VaccineTypeEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface WarehouseService {

    /**
     * Lấy danh sách VaccineBatch (có tìm kiếm + phân trang)
     *
     * @param searchType loại tìm kiếm (name, type, origin, age), null/empty = tất cả
     * @param keyword từ khóa tìm kiếm
     * @param pageNo số trang (0-based)
     * @param pageSize số item mỗi trang
     * @return danh sách phân trang WarehouseResponse
     */
    Page<WarehouseResponse> getWarehouses(String searchType, String keyword, int pageNo, int pageSize);

    /**
     * Import một batch vắc-xin từ request
     */
    ImportResponse importVaccine(ImportRequest request);

    /**
     * Lấy danh sách tên các loại vắc-xin (VaccineType)
     */
    List<String> getAllVaccineTypeNames();
}

