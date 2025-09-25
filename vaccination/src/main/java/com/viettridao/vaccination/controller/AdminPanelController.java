package com.viettridao.vaccination.controller;

import java.util.List;

import com.viettridao.vaccination.dto.request.adminPanel.TaiKhoanCreateRequest;
import com.viettridao.vaccination.model.VaiTroEntity;
import com.viettridao.vaccination.repository.VaiTroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.viettridao.vaccination.dto.request.adminPanel.LichTiemRequest;
import com.viettridao.vaccination.dto.response.adminPanel.LichTiemResponse;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.service.LichTiemService;
import com.viettridao.vaccination.service.TaiKhoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/adminpanel")
@RequiredArgsConstructor
public class AdminPanelController {

    private final LichTiemService lichTiemService;
    private final TaiKhoanService taiKhoanService;
    private final VaiTroRepository vaiTroRepository;

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
            // Trả lại dữ liệu đã nhập và danh sách vai trò cho FE
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
        return vaiTroRepository.findAllByIsDeletedFalse(); // hoặc findAll() nếu không có soft-delete
    }

    @GetMapping("/schedule")
    public String viewSchedule(Model model, @RequestParam(required = false) String maVacXin) {

        List<LichTiemResponse> lichTiemList = lichTiemService.getDanhSachLichTiem();

        for (LichTiemResponse lich : lichTiemList) {
            List<LichTiemResponse.DonThuocDTO> danhSachBN =
                    lichTiemService.getDanhSachBenhNhanTheoLich(lich.getMaLich(), maVacXin);
            lich.setDanhSachDonThuoc(danhSachBN);
        }

        // Danh sách bác sĩ
        model.addAttribute("tatCaBacSi", taiKhoanService.getTatCaBacSiHoatDong());

        // Form tạo mới → bacSiIds rỗng
        LichTiemRequest newRequest = new LichTiemRequest();
        newRequest.setBacSiIds(List.of()); // đảm bảo rỗng
        model.addAttribute("lichTiemRequest", newRequest);

        model.addAttribute("lichTiemList", lichTiemList);
        model.addAttribute("tatCaLoaiVacXin", lichTiemService.getTatCaLoaiVacXin());
        model.addAttribute("maVacXin", maVacXin);
        model.addAttribute("pageTitle", "Quản lý lịch tiêm chủng");

        return "/adminpanel/schedule";
    }

    // Xử lý lưu lịch mới
    @PostMapping("/schedule")
    public String saveSchedule(@Valid @ModelAttribute("lichTiemRequest") LichTiemRequest request,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return viewSchedule(model, request.getMaVacXin());
        }
        lichTiemService.createLichTiem(request);
        return "redirect:/adminpanel/schedule";
    }

    @GetMapping("/schedule/edit/{maLich}")
    public String editScheduleForm(@PathVariable String maLich, Model model) {
        LichTiemRequest request = lichTiemService.getLichTiemRequestById(maLich);
        model.addAttribute("lichTiemRequest", request);
        model.addAttribute("tatCaBacSi", taiKhoanService.getTatCaBacSiHoatDong());
        return "/adminpanel/schedule";
    }


    @PostMapping("/schedule/edit/{maLich}")
    public String updateSchedule(@PathVariable String maLich,
                                 @Valid @ModelAttribute("lichTiemRequest") LichTiemRequest request, BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("maLich", maLich);
            return "schedule";
        }
        lichTiemService.createOrUpdateLichTiem(request, maLich);
        return "redirect:/adminpanel/schedule";
    }

    @PostMapping("/schedule/delete/{maLich}")
    public String deleteSchedule(@PathVariable String maLich) {
        lichTiemService.deleteLichTiem(maLich);
        return "redirect:/adminpanel/schedule";
    }
}
