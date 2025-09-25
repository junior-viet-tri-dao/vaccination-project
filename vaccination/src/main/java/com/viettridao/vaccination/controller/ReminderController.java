package com.viettridao.vaccination.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.viettridao.vaccination.dto.request.supportemployee.NhacNhoTiemRequest;
import com.viettridao.vaccination.dto.response.supportemployee.NhacNhoTiemResponse;
import com.viettridao.vaccination.service.NhacNhoTiemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final NhacNhoTiemService nhacNhoTiemService;

    // Helper method để lấy vai trò đầu tiên (ưu tiên ADMIN nếu có)
    private String getUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.replace("ROLE_", ""))
                .findFirst()
                .orElse("");
    }

    @GetMapping("")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('READ_REMINDER')) or hasRole('ADMIN')")
    public String showReminderList(Model model, Authentication authentication) {
        // Lấy danh sách tất cả bệnh nhân có lịch sử/dự kiến tiêm
        List<NhacNhoTiemResponse> allReminders = nhacNhoTiemService.getAllReminders();

        // Gán vào model để Thymeleaf render list.html
        model.addAttribute("reminderList", allReminders);
        model.addAttribute("pageTitle", "Danh sách nhắc nhở tiêm chủng");
        model.addAttribute("userRole", getUserRole(authentication));

        return "supportEmployee/reminder-list"; // tên file list.html
    }

    // Hiển thị form nhắc nhở tiêm chủng
    @GetMapping("/{maBenhNhan}")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('READ_REMINDER')) or hasRole('ADMIN')")
    public String showReminderForm(@PathVariable String maBenhNhan, Model model, Authentication authentication) {
        List<NhacNhoTiemResponse> lichSuVaDuKien = nhacNhoTiemService.getLichSuVaDuKien(maBenhNhan);

        NhacNhoTiemRequest request = NhacNhoTiemRequest.builder()
                .email(!lichSuVaDuKien.isEmpty() ? lichSuVaDuKien.get(0).getEmail() : null)
                .lichTiem(lichSuVaDuKien.stream()
                        .map(resp -> NhacNhoTiemRequest.ThongTinTiemDto.builder().ngayTiem(resp.getNgayTiem())
                                .loaiVacXinDaTiem(resp.getLoaiVacXinDaTiem()).ngayDuKien(resp.getNgayTiemDuKien())
                                .loaiVacXinDuKien(resp.getLoaiVacXinDuKien()).gia(resp.getGia()).build())
                        .toList())
                .build();

        model.addAttribute("pageTitle", "Nhắc lịch tiêm chủng");
        model.addAttribute("nhacNhoRequest", request);
        model.addAttribute("userRole", getUserRole(authentication));

        return "supportEmployee/reminder-form";
    }

    @PostMapping("/send")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('SEND_REMINDER')) or hasRole('ADMIN')")
    public String sendReminderEmail(@Validated @ModelAttribute("nhacNhoRequest") NhacNhoTiemRequest request,
                                    Model model, Authentication authentication) {
        try {
            nhacNhoTiemService.sendEmail(request);
            model.addAttribute("successMessage", "Email đã được gởi thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi gởi email: " + e.getMessage());
        }

        model.addAttribute("pageTitle", "Danh sách nhắc nhở tiêm chủng");
        model.addAttribute("reminderList", nhacNhoTiemService.getAllReminders());
        model.addAttribute("userRole", getUserRole(authentication));
        return "supportEmployee/reminder-list";
    }
}