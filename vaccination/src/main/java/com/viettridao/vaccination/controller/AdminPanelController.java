package com.viettridao.vaccination.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse.DonThuocDto;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.service.LichTiemService;
import com.viettridao.vaccination.service.TaiKhoanService;
import com.viettridao.vaccination.service.VacXinService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adminpanel")
public class AdminPanelController {

    private final LichTiemService lichTiemService;
    private final VacXinService vacXinService;
    private final TaiKhoanService taiKhoanService;



    @GetMapping("/schedule")
    public String getSchedulePage(Model model) {
        List<LichTiemResponse> lichs = lichTiemService.getAllLichTiemDangHoatDong();
        model.addAttribute("lichList", lichs);

        List<TaiKhoanEntity> bacSiList = taiKhoanService.getAllDoctors();
        model.addAttribute("bacSiList", bacSiList);

        List<VacXinEntity> vacXinList = vacXinService.getAllActiveVaccines();
        model.addAttribute("vacXinList", vacXinList);

        // Lấy tất cả bệnh nhân
        List<DonThuocDto> allPatients = lichTiemService.getAllDonThuoc();

        // Lấy danh sách bệnh nhân chưa có lịch
        List<DonThuocDto> patientsWithoutSchedule = allPatients.stream()
                .filter(don -> lichs.stream().noneMatch(lich ->
                        lich.getDanhSachDonThuoc().stream()
                                .anyMatch(d -> d.getMaDon().equals(don.getMaDon()))
                ))
                .collect(Collectors.toList());

        model.addAttribute("patientsWithoutSchedule", patientsWithoutSchedule);

        model.addAttribute("lichTiemRequest", new LichTiemRequest());
        model.addAttribute("pageTitle", "Quản lý lịch tiêm chủng");

        return "adminpanel/schedule";
    }




    @PostMapping("/schedule")
    public String saveSchedule(@ModelAttribute("lichTiemRequest") @Valid LichTiemRequest request,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Quản lý lịch tiêm chủng");
            return "adminpanel/schedule";
        }

        try {
            lichTiemService.create(request, userDetails.getUsername());
            redirectAttributes.addFlashAttribute("success", "Thêm lịch thành công");
        } catch (Exception e) {
            e.printStackTrace(); 
            redirectAttributes.addFlashAttribute("error", "Thêm lịch thất bại: " + e.getMessage());
        }


        return "redirect:/adminpanel/schedule";
    }
}

