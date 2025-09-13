package com.viettridao.vaccination.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettridao.vaccination.dto.request.warehouse.ExportRequest;
import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.HoaDonChuaNhapResponse;
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
        Page<WarehouseResponse> warehousePage = warehouseService.getWarehouses(searchType, keyword, page, size);

        // Nếu page bị chỉnh -> lấy lại dữ liệu
        if (page != warehousePage.getNumber()) {
            warehousePage = warehouseService.getWarehouses(searchType, keyword, page, size);
        }

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
        model.addAttribute("importRequest", new ImportRequest());

        // Lấy danh sách số hóa đơn chưa nhập kho để đổ vào dropdown
        List<HoaDonChuaNhapResponse> hoaDonChuaNhapList = warehouseService.getHoaDonChuaNhap();
        model.addAttribute("hoaDonChuaNhapList", hoaDonChuaNhapList);

        String hoaDonChuaNhapListJson;
        try {
            hoaDonChuaNhapListJson = new ObjectMapper().writeValueAsString(hoaDonChuaNhapList);
        } catch (JsonProcessingException e) {
            hoaDonChuaNhapListJson = "[]";
        }
        model.addAttribute("hoaDonChuaNhapListJson", hoaDonChuaNhapListJson);

        return "warehouse/importvaccine";
    }

    @PostMapping("/importvaccine")
    public String importVaccine(
            @Valid @ModelAttribute("importRequest") ImportRequest importRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("tab", "import");

        List<HoaDonChuaNhapResponse> hoaDonChuaNhapList = warehouseService.getHoaDonChuaNhap();
        model.addAttribute("hoaDonChuaNhapList", hoaDonChuaNhapList);

        String hoaDonChuaNhapListJson;
        try {
            hoaDonChuaNhapListJson = new ObjectMapper().writeValueAsString(hoaDonChuaNhapList);
        } catch (JsonProcessingException e) {
            hoaDonChuaNhapListJson = "[]";
        }
        model.addAttribute("hoaDonChuaNhapListJson", hoaDonChuaNhapListJson);

        if (bindingResult.hasErrors()) {
            return "warehouse/importvaccine";
        }

        ImportResponse response = warehouseService.importVaccine(importRequest);
        redirectAttributes.addFlashAttribute("success", "Nhập kho vắc-xin thành công!");
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
        Page<WarehouseResponse> warehousePage = warehouseService.getWarehouses(null, null, page, size);

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
    public String exportVaccine(
            @Valid @ModelAttribute("exportRequest") ExportRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        model.addAttribute("tab", "export");

        if (bindingResult.hasErrors()) {
            return "warehouse/exportvaccine";
        }

        try {
            WarehouseResponse response = warehouseService.exportVaccine(request);
            redirectAttributes.addFlashAttribute("success",
                    "Xuất thành công lô " + response.getMaLoCode()
                            + " - Số lượng còn lại: " + response.getSoLuong());
            return "redirect:/warehouse";
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().contains("Số lượng")) {
                bindingResult.rejectValue("quantity", "error.quantity", ex.getMessage());
            } else if (ex.getMessage().contains("Không tìm thấy")) {
                bindingResult.rejectValue("maLoCode", "error.maLoCode", ex.getMessage());
            } else {
                bindingResult.reject("error.global", ex.getMessage());
            }
            return "warehouse/exportvaccine";
        }
    }
}