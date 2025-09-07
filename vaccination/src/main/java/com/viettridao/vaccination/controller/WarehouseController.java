package com.viettridao.vaccination.controller;

import com.viettridao.vaccination.dto.request.warehouse.ExportRequest;
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
        // Luôn đẩy danh sách loại vắc-xin trở lại form nếu có lỗi
        model.addAttribute("tab", "import");

        if (bindingResult.hasErrors()) {
            return "warehouse/importvaccine"; // trả lại form với dữ liệu người dùng nhập
        }

        // Gọi Service import
        ImportResponse response = warehouseService.importVaccine(importRequest);

        // Thêm thông báo thành công vào redirect
        redirectAttributes.addFlashAttribute("success",
                "Nhập kho vắc-xin thành công!");

        // Chuyển về trang import (hoặc danh sách kho)
        return "redirect:/warehouse";
    }

    /**
     * Hiển thị form export vắc-xin + danh sách lô
     */
    @GetMapping("/exportvaccine")
    public String showExportVaccineForm(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model
    ) {
        // Lấy tất cả lô vắc-xin có phân trang
        Page<WarehouseResponse> warehousePage =
                warehouseService.getWarehouses(null, null, page, size);

        // Fix lại nếu page vượt ngoài range
        if (page != warehousePage.getNumber()) {
            warehousePage = warehouseService.getWarehouses(null, null, page, size);
        }

        model.addAttribute("tab", "export");
        model.addAttribute("pageTitle", "Xuất vắc xin");
        model.addAttribute("warehousePage", warehousePage);
        model.addAttribute("currentPage", warehousePage.getNumber());
        model.addAttribute("pageSize", size);
        model.addAttribute("exportRequest", new ExportRequest());

        return "warehouse/exportvaccine";
    }

    /**
     * Xử lý submit form export
     */
    @PostMapping("/exportvaccine")
    public String exportVaccine(@Valid @ModelAttribute("exportRequest") ExportRequest request,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "warehouse/exportvaccine"; // trả lại view nếu có lỗi
        }

        WarehouseResponse response = warehouseService.exportVaccine(request.getBatchCode(), request.getQuantity());
        redirectAttributes.addFlashAttribute("success",
                "Xuất thành công lô " + response.getBatchCode() +
                        " - Số lượng còn lại: " + response.getQuantity());

        return "redirect:/warehouse";
    }
}