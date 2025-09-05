package com.viettridao.vaccination.controller;

import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public String getWarehouses(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            Model model
    ) {
        // Gán mặc định nếu keyword chưa nhập
        if (keyword == null || keyword.isBlank()) {
            searchType = "name";
        }

        // Lấy dữ liệu đã xử lý logic page từ Service
        Page<WarehouseResponse> warehousePage =
                warehouseService.getWarehouses(searchType, keyword, page, size);

        // Nếu page bị chỉnh -> lấy lại dữ liệu
        if (page != warehousePage.getNumber()) {
            warehousePage = warehouseService.getWarehouses(searchType, keyword, page, size);
        }

        // Gửi dữ liệu xuống view
        model.addAttribute("tab", "warehouse");
        model.addAttribute("pageTitle", "Quản lý kho vắc xin");
        model.addAttribute("warehousePage", warehousePage);
        model.addAttribute("currentPage", warehousePage.getNumber());
        model.addAttribute("pageSize", size);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "warehouse/warehouse";
    }

    /**
     * Hiển thị form import vắc-xin
     */
    @GetMapping("/importvaccine")
    public String showImportVaccineForm(Model model) {
        model.addAttribute("tab", "import");
        model.addAttribute("importRequest", new ImportRequest()); // DTO trống cho form

        // Lấy danh sách loại vắc-xin từ DB
        List<String> vaccineTypes = warehouseService.getAllVaccineTypeNames();
        model.addAttribute("vaccineTypes", vaccineTypes);
        return "warehouse/importvaccine";
    }

    /**
     * Xử lý submit form import
     */
    @PostMapping("/importvaccine")
    public String importVaccine(
            @Valid @ModelAttribute("importRequest") ImportRequest importRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // Nếu có lỗi validate → trả lại form
        if (bindingResult.hasErrors()) {
            model.addAttribute("tab", "import");
            return "warehouse/importvaccine";
        }

        // Gọi Service import
        ImportResponse response = warehouseService.importVaccine(importRequest);

        // Thêm thông báo thành công vào redirect
        redirectAttributes.addFlashAttribute("successMessage",
                "Nhập kho vắc-xin thành công! Batch ID: " + response.getBatchId());

        // Chuyển về trang import (hoặc danh sách kho)
        return "redirect:/warehouse/importvaccine";
    }

    @GetMapping("/exportvaccine")
    public String showExportVaccineForm(Model model) {
        model.addAttribute("tab", "export");
        return "warehouse/exportvaccine";
    }
}