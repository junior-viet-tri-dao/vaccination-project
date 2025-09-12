package com.viettridao.vaccination.controller;

import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.service.VaccineListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/normaluser")
public class NormalUserController {
    private final VaccineListService vaccineListService;

    // Hiển thị trang danh sách vắc xin
    @GetMapping("/view-vaccines")
    public String showVaccineList(
            @RequestParam(name = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {
        Page<VaccineListResponse> vaccinePage = vaccineListService.getVaccines(searchType, keyword, page, size);

        model.addAttribute("vaccinePage", vaccinePage);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "normalUser/vaccine-list";
    }
}