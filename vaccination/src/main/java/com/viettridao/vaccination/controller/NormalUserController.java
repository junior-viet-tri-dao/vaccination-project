package com.viettridao.vaccination.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.viettridao.vaccination.dto.request.normalUser.EditProfileRequest;
import com.viettridao.vaccination.dto.request.normalUser.PhanHoiCapCaoRequest;
import com.viettridao.vaccination.dto.request.normalUser.PhanHoiSauTiemRequest;
import com.viettridao.vaccination.dto.response.DichBenhResponse;
import com.viettridao.vaccination.dto.response.normalUser.PhanHoiSauTiemResponse;
import com.viettridao.vaccination.dto.response.normalUser.ProfileDetailResponse;
import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.dto.response.normalUser.VaccineScheduleResponse;
import com.viettridao.vaccination.model.PhanHoiEntity;
import com.viettridao.vaccination.service.DichBenhService;
import com.viettridao.vaccination.service.KetQuaTiemService;
import com.viettridao.vaccination.service.PhanHoiCapCaoService;
import com.viettridao.vaccination.service.PhanHoiSauTiemService;
import com.viettridao.vaccination.service.ProfileService;
import com.viettridao.vaccination.service.VaccineListService;
import com.viettridao.vaccination.service.VaccineScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/normalUser")
public class NormalUserController {
    private final VaccineListService vaccineListService;
    private final VaccineScheduleService vaccineScheduleService;
    private final ProfileService profileService;
    private final DichBenhService dichBenhService;
    private final PhanHoiCapCaoService phanHoiService;
    private final PhanHoiSauTiemService phanHoiSauTiemService;
    private final KetQuaTiemService ketQuaTiemService;

    // Helper method để lấy vai trò đầu tiên (ưu tiên ADMIN nếu có)
    private String getUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.replace("ROLE_", ""))
                .findFirst()
                .orElse("");
    }

    // Hiển thị trang danh sách vắc xin
    @GetMapping("/view-vaccines")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('VIEW_VACCINE')) or hasRole('ADMIN')")
    public String showVaccineList(
            @RequestParam(name = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Model model,
            Authentication authentication) {
        Page<VaccineListResponse> vaccinePage = vaccineListService.getVaccines(searchType, keyword, page, size);

        model.addAttribute("vaccinePage", vaccinePage);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("userRole", getUserRole(authentication));

        return "normalUser/vaccine-list";
    }

    // Tra cứu lịch tiêm phòng cho user đang đăng nhập
    @GetMapping("/vaccine-schedule")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('VIEW_SCHEDULE')) or hasRole('ADMIN')")
    public String showSchedule(Model model, Authentication authentication) {
        String tenDangNhap = authentication.getName();
        String benhNhanId = profileService.getProfileDetailByUsername(tenDangNhap).getId();
        List<VaccineScheduleResponse> schedules = vaccineScheduleService.getVaccineSchedulesForUser(benhNhanId);
        model.addAttribute("schedules", schedules);
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/vaccine-schedule";
    }

    // Xem hồ sơ cá nhân của user đang đăng nhập
    @GetMapping("/view-profile")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('READ_PROFILE')) or hasRole('ADMIN')")
    public String viewHistory(Model model, Authentication authentication) {
        String tenDangNhap = authentication.getName();
        ProfileDetailResponse profile = profileService.getProfileDetailByUsername(tenDangNhap);

        model.addAttribute("pageTitle", "Hồ sơ thông tin cá nhân");
        model.addAttribute("profile", profile);
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/view-profile";
    }

    // Sửa thông tin cá nhân - Hiển thị form với thông tin cũ
    @GetMapping("/edit-profile")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('UPDATE_PROFILE')) or hasRole('ADMIN')")
    public String editProfile(Model model, Authentication authentication) {
        String tenDangNhap = authentication.getName();
        EditProfileRequest editProfileRequest = profileService.getEditProfileRequestByUsername(tenDangNhap);
        model.addAttribute("pageTitle", "Sửa đổi thông tin cá nhân");
        model.addAttribute("editProfileRequest", editProfileRequest);
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/edit-profile";
    }

    // Sửa thông tin cá nhân - Submit cập nhật
    @PostMapping("/edit-profile")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('UPDATE_PROFILE')) or hasRole('ADMIN')")
    public String updateProfile(@ModelAttribute("editProfileRequest") @Valid EditProfileRequest editProfileRequest,
                                BindingResult bindingResult, Authentication authentication, Model model,
                                RedirectAttributes redirectAttributes) {
        String tenDangNhap = authentication.getName();

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Sửa đổi thông tin cá nhân");
            model.addAttribute("userRole", getUserRole(authentication));
            return "normalUser/edit-profile";
        }
        profileService.updateProfileByUsername(tenDangNhap, editProfileRequest);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin cá nhân thành công!");
        return "redirect:/normalUser/view-profile";
    }

    /**
     * Hiển thị danh sách tình hình dịch bệnh có phân trang.
     */
    @GetMapping("/epidemic")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('VIEW_EPIDEMIC')) or hasRole('ADMIN')")
    public String showDiseaseReport(Model model, @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size, Authentication authentication) {

        Page<DichBenhResponse> dichBenhPage = dichBenhService.getAllActiveDichBenh(PageRequest.of(page, size));

        int startStt = page * size + 1;
        int idx = 0;
        for (DichBenhResponse response : dichBenhPage.getContent()) {
            response.setStt(startStt + idx);
            idx++;
        }

        model.addAttribute("pageTitle", "Tình hình dịch bệnh tại địa phương");
        model.addAttribute("dichBenhPage", dichBenhPage);
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/epidemic";
    }

    @GetMapping("/feedback-highlevel")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK')) or hasRole('ADMIN')")
    public String showHighFeedbackForm(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Phản hồi cấp cao");
        if (!model.containsAttribute("phanHoiCapCaoRequest")) {
            model.addAttribute("phanHoiCapCaoRequest", new PhanHoiCapCaoRequest());
        }
        model.addAttribute("dsLoaiPhanHoi", PhanHoiEntity.LoaiPhanHoi.values());
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/feedback-highlevel";
    }

    @PostMapping("/feedback-highlevel")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK')) or hasRole('ADMIN')")
    public String submitHighFeedback(@ModelAttribute("phanHoiCapCaoRequest") @Valid PhanHoiCapCaoRequest request,
                                     BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                                     Authentication authentication) {
        model.addAttribute("pageTitle", "Phản hồi cấp cao");
        model.addAttribute("dsLoaiPhanHoi", PhanHoiEntity.LoaiPhanHoi.values());
        model.addAttribute("userRole", getUserRole(authentication));
        if (bindingResult.hasErrors()) {
            model.addAttribute("hasError", true);
            return "normalUser/feedback-highlevel";
        }
        try {
            phanHoiService.guiPhanHoiCapCao(request);
            redirectAttributes.addFlashAttribute("successMessage", "Gửi phản hồi thành công!");
            return "redirect:/normalUser/view-profile";
        } catch (Exception e) {
            model.addAttribute("hasError", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "normalUser/feedback-highlevel";
        }
    }

    @GetMapping("/feedback")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK')) or hasRole('ADMIN')")
    public String showFeedbackForm(@RequestParam(name = "ketQuaTiemId") String ketQuaTiemId, Model model,
                                   RedirectAttributes redirectAttributes, Authentication authentication) {
        List<PhanHoiSauTiemResponse> danhSachCanPhanHoi = phanHoiSauTiemService.getKetQuaTiemCanPhanHoiByCurrentUser();
        PhanHoiSauTiemResponse kq = danhSachCanPhanHoi.stream().filter(p -> p.getKetQuaTiemId().equals(ketQuaTiemId))
                .findFirst().orElse(null);

        if (kq == null) {
            redirectAttributes.addFlashAttribute("hasError", true);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không tìm thấy mũi tiêm cần phản hồi hoặc đã phản hồi.");
            return "redirect:/normalUser/completed-vaccines";
        }

        model.addAttribute("kq", kq);

        PhanHoiSauTiemRequest request = new PhanHoiSauTiemRequest();
        request.setKetQuaTiemId(ketQuaTiemId);
        model.addAttribute("phanHoiSauTiemRequest", request);

        model.addAttribute("pageTitle", "Gửi phản hồi sau tiêm phòng");

        boolean canPhanHoi = !kq.getTrangThaiPhanHoi().equals("DA_PHAN_HOI");
        model.addAttribute("canPhanHoi", canPhanHoi);
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/feedback-form";
    }

    @PostMapping("/feedback")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK')) or hasRole('ADMIN')")
    public String submitFeedback(@ModelAttribute("phanHoiSauTiemRequest") @Valid PhanHoiSauTiemRequest request,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        model.addAttribute("pageTitle", "Gửi phản hồi sau tiêm phòng");
        model.addAttribute("userRole", getUserRole(authentication));

        if (bindingResult.hasErrors()) {
            PhanHoiSauTiemResponse kq = phanHoiSauTiemService.getByKetQuaTiemId(request.getKetQuaTiemId());
            model.addAttribute("kq", kq);
            model.addAttribute("hasError", true);
            return "normalUser/feedback-form";
        }

        try {
            phanHoiSauTiemService.taoPhanHoiSauTiem(request);
            redirectAttributes.addFlashAttribute("success", "Gửi phản hồi sau tiêm thành công!");
            return "redirect:/normalUser/completed-vaccines";
        } catch (Exception e) {
            PhanHoiSauTiemResponse kq = phanHoiSauTiemService.getByKetQuaTiemId(request.getKetQuaTiemId());
            model.addAttribute("kq", kq);
            model.addAttribute("hasError", true);
            model.addAttribute("errorMessage", e.getMessage());
            return "normalUser/feedback-form";
        }
    }

    /**
     * Hiển thị danh sách các mũi tiêm đã hoàn thành cần phản hồi
     */
    @GetMapping("/completed-vaccines")
    @PreAuthorize("(hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK')) or hasRole('ADMIN')")
    public String showCompletedVaccines(Model model, Authentication authentication) {
        List<PhanHoiSauTiemResponse> danhSachCanPhanHoi = phanHoiSauTiemService.getKetQuaTiemCanPhanHoiByCurrentUser();

        model.addAttribute("completedList", danhSachCanPhanHoi);
        model.addAttribute("pageTitle", "Danh sách mũi tiêm cần phản hồi");
        model.addAttribute("userRole", getUserRole(authentication));
        return "normalUser/completed-vaccine-list";
    }

}