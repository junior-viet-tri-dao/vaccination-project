//package com.viettridao.vaccination.controller;
//
//import com.viettridao.vaccination.dto.response.EpidemicResponse;
//import com.viettridao.vaccination.service.EpidemicService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * EpidemicController
// * Controller quản lý luồng hiển thị thông tin dịch bệnh.
// */
//@Controller
//@RequiredArgsConstructor
//public class EpidemicController {
//
//    private final EpidemicService epidemicService;
//
//    /**
//     * Hiển thị danh sách tình hình dịch bệnh có phân trang.
//     *
//     * @param model model để truyền dữ liệu sang view
//     * @param page  số trang hiện tại (default = 0)
//     * @param size  số bản ghi mỗi trang (default = 10)
//     * @return tên file Thymeleaf view
//     */
//    @GetMapping("/epidemic")
//    public String showDiseaseReport(
//            Model model,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
////        // Lấy danh sách epidemic từ service
////        Page<EpidemicResponse> epidemicPage = epidemicService.getAllEpidemics(page, size);
////
////        // Truyền dữ liệu sang Thymeleaf
////        model.addAttribute("pageTitle", "Tình hình dịch bệnh tại địa phương");
////        model.addAttribute("epidemicPage", epidemicPage);
//
//        return "normalUser/epidemic";
//    }
//}
