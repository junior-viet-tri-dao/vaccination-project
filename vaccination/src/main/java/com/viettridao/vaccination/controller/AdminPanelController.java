package com.viettridao.vaccination.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import com.viettridao.vaccination.dto.request.adminPanel.TaiKhoanCreateRequest;
import com.viettridao.vaccination.model.VaiTroEntity;
import com.viettridao.vaccination.repository.VaiTroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/adminpanel")
@RequiredArgsConstructor
public class AdminPanelController {

    private final LichTiemService lichTiemService;
    private final TaiKhoanService taiKhoanService;
    private final VaiTroRepository vaiTroRepository;
    private final VacXinService vacXinService;

    // Hiển thị form đăng ký tài khoản
    @GetMapping("/register")
    public String showRegisterUserForm(Model model) {
        model.addAttribute("taiKhoanCreateRequest", new TaiKhoanCreateRequest());
        model.addAttribute("danhSachVaiTro", vaiTroRepository.findAllByIsDeletedFalse());
        return "adminPanel/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("taiKhoanCreateRequest") TaiKhoanCreateRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("danhSachVaiTro", vaiTroRepository.findAllByIsDeletedFalse());

        if (bindingResult.hasErrors()) {
            return "adminPanel/register";
        }
        try {
            taiKhoanService.createTaiKhoan(request);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("tenDangNhap", "error.taiKhoanCreateRequest", e.getMessage());
            return "adminPanel/register";
        }
        return "redirect:/adminpanel/dashboard";
    }

    // Hàm lấy danh sách vai trò, bạn có thể tuỳ chỉnh dùng service hoặc repo
    private List<VaiTroEntity> getDanhSachVaiTro() {
        return vaiTroRepository.findAllByIsDeletedFalse();
    }

    // ------------------- Lịch tiêm -------------------
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
            // Thêm lại các attribute cần thiết để render form
            model.addAttribute("pageTitle", "Quản lý lịch tiêm chủng");
            model.addAttribute("lichList", lichTiemService.getAllLichTiemDangHoatDong());
            model.addAttribute("bacSiList", taiKhoanService.getAllDoctors());
            model.addAttribute("vacXinList", vacXinService.getAllActiveVaccines());

            List<LichTiemResponse> lichs = lichTiemService.getAllLichTiemDangHoatDong();
            List<DonThuocDto> allPatients = lichTiemService.getAllDonThuoc();
            List<DonThuocDto> patientsWithoutSchedule = allPatients.stream()
                    .filter(don -> lichs.stream().noneMatch(lich ->
                            lich.getDanhSachDonThuoc().stream()
                                    .anyMatch(d -> d.getMaDon().equals(don.getMaDon()))
                    ))
                    .collect(Collectors.toList());
            model.addAttribute("patientsWithoutSchedule", patientsWithoutSchedule);

            return "adminpanel/schedule";
        }

        try {
            lichTiemService.create(request, userDetails.getUsername());
            redirectAttributes.addFlashAttribute("success", "Thêm lịch thành công");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Thêm lịch thất bại: " + e.getMessage());
        }

        return "redirect:/adminpanel/dashboard";
    }
}