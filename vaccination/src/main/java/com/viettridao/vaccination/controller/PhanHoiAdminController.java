package com.viettridao.vaccination.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.viettridao.vaccination.dto.response.PhanHoiAdminResponse;
import com.viettridao.vaccination.service.PhanHoiCapCaoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adminpanel/phanhoiAdmin")
public class PhanHoiAdminController {

    private final PhanHoiCapCaoService phanHoiCapCaoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listPhanHoiCapCao(Model model) {
        List<PhanHoiAdminResponse> dsPhanHoi = phanHoiCapCaoService.getAllForAdmin();
        model.addAttribute("phanHoiList", dsPhanHoi);
        return "adminpanel/phanhoiAdmin";
    }
}