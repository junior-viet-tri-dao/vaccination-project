package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.response.warehouse.HoaDonChuaNhapResponse;
import org.springframework.data.domain.Page;
import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.request.warehouse.ExportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;

import java.util.List;


public interface WarehouseService {

    /**
     * Lấy danh sách LoVacXin (có tìm kiếm + phân trang)
     *
     * @param searchType loại tìm kiếm (name, type, origin, age), null/empty = tất cả
     * @param keyword từ khóa tìm kiếm
     * @param pageNo số trang (0-based)
     * @param pageSize số item mỗi trang
     * @return danh sách phân trang WarehouseResponse
     */
    Page<WarehouseResponse> getWarehouses(String searchType, String keyword, int pageNo, int pageSize);

    /**
     * Import một lô vắc-xin từ request
     */
    ImportResponse importVaccine(ImportRequest request);

    /**
     * Xuất kho
     */
    WarehouseResponse exportVaccine(ExportRequest request);

    List<HoaDonChuaNhapResponse> getHoaDonChuaNhap();

}