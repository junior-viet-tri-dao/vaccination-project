package com.viettridao.vaccination.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    // Helper method để lấy vai trò đầu tiên (ưu tiên ADMIN nếu có)
    private String getUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.replace("ROLE_", ""))
                .findFirst()
                .orElse("");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('WAREHOUSE') and hasAuthority('VIEW_WAREHOUSE'))")
    public String getWarehouses(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model,
            Authentication authentication) {

        Page<WarehouseResponse> warehousePage = warehouseService.getWarehouses(searchType, keyword, page, size);

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

        // Truyền userRole cho giao diện phân biệt nút quay lại/thoát
        model.addAttribute("userRole", getUserRole(authentication));

        return "warehouse/warehouse";
    }

    /**
     * Hiển thị form import vắc-xin
     */
    @GetMapping("/importvaccine")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('WAREHOUSE') and hasAuthority('CREATE_IMPORT_WAREHOUSE'))")
    public String showImportVaccineForm(Model model, Authentication authentication) {
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

        // Truyền userRole cho giao diện
        model.addAttribute("userRole", getUserRole(authentication));

        return "warehouse/importvaccine";
    }

    @PostMapping("/importvaccine")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('WAREHOUSE') and hasAuthority('CREATE_IMPORT_WAREHOUSE'))")
    public String importVaccine(@Valid @ModelAttribute("importRequest") ImportRequest importRequest,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
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

        // Truyền userRole cho giao diện
        model.addAttribute("userRole", getUserRole(authentication));

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
    @PreAuthorize("hasRole('ADMIN') or (hasRole('WAREHOUSE') and hasAuthority('CREATE_EXPORT_WAREHOUSE'))")
    public String showExportVaccineForm(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size, Model model, Authentication authentication) {
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

        // Truyền userRole cho giao diện
        model.addAttribute("userRole", getUserRole(authentication));

        return "warehouse/exportvaccine";
    }

    /**
     * Xử lý submit form export
     */
    @PostMapping("/exportvaccine")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('WAREHOUSE') and hasAuthority('CREATE_EXPORT_WAREHOUSE'))")
    public String exportVaccine(@Valid @ModelAttribute("exportRequest") ExportRequest request,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
        model.addAttribute("tab", "export");

        // Truyền userRole cho giao diện
        model.addAttribute("userRole", getUserRole(authentication));

        if (bindingResult.hasErrors()) {
            return "warehouse/exportvaccine";
        }

        try {
            WarehouseResponse response = warehouseService.exportVaccine(request);
            redirectAttributes.addFlashAttribute("success",
                    "Xuất thành công lô " + response.getMaLoCode() + " - Số lượng còn lại: " + response.getSoLuong());
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