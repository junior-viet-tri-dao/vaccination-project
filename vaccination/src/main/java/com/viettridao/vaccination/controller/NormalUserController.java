package com.viettridao.vaccination.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import com.viettridao.vaccination.dto.response.DichBenhResponse;
import com.viettridao.vaccination.dto.response.normalUser.ProfileDetailResponse;
import com.viettridao.vaccination.dto.response.normalUser.VaccineListResponse;
import com.viettridao.vaccination.dto.response.normalUser.VaccineScheduleResponse;
import com.viettridao.vaccination.model.PhanHoiEntity;
import com.viettridao.vaccination.service.DichBenhService;
import com.viettridao.vaccination.service.PhanHoiCapCaoService;
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
    
    


    // Hiển thị trang danh sách vắc xin
    @GetMapping("/view-vaccines")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('VIEW_VACCINE') or hasRole('ADMIN')")
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

//    @GetMapping("/vaccine-schedule")
//    public String showSchedule(Model model, Authentication authentication) {
//        // Lấy userId từ authentication (giả sử username là userId hoặc bạn cần lấy từ principal)
//        String userId = authentication.getName(); // hoặc lấy id trực tiếp từ principal nếu có
//
//        List<VaccineScheduleResponse> schedules = vaccineScheduleService.getVaccineSchedulesForUser(userId);
//        model.addAttribute("schedules", schedules);
//
//        return "normalUser/vaccine-schedule";  // resources/templates/vaccine-schedule.html
//    }

    @GetMapping("/vaccine-schedule")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('VIEW_SCHEDULE') or hasRole('ADMIN')")
    public String showSchedule(Model model, Authentication authentication) {
        String userId;
        if (authentication == null) {
            // Gán tạm mã bệnh nhân test (chỉnh theo data thật của bạn)
            userId = "bn1-0000-0000-0000-000000000001";
        } else {
            userId = authentication.getName();
        }

        List<VaccineScheduleResponse> schedules = vaccineScheduleService.getVaccineSchedulesForUser(userId);
        model.addAttribute("schedules", schedules);

        return "normalUser/vaccine-schedule";
    }


    // Xem hồ sơ cá nhân
    @GetMapping("/view-profile")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('READ_PROFILE') or hasRole('ADMIN')")
    public String viewHistory(Model model, Authentication authentication) {
        String benhNhanId = getBenhNhanIdFromAuth(authentication);

        ProfileDetailResponse profile = profileService.getProfileDetail(benhNhanId);

        model.addAttribute("pageTitle", "Hồ sơ tiêm phòng");
        model.addAttribute("profile", profile);

        return "normalUser/view-profile";
    }

    // Helper để lấy benhNhanId từ Authentication
    private String getBenhNhanIdFromAuth(Authentication authentication) {
        if (authentication == null) {
            // Gán tạm mã bệnh nhân test (chỉnh theo data thật của bạn)
            return "bn1-0000-0000-0000-000000000001";
        }
        return authentication.getName();
    }

    // Sửa thông tin cá nhân - Hiển thị form với thông tin cũ
    @GetMapping("/edit-profile")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('UPDATE_PROFILE') or hasRole('ADMIN')")
    public String editProfile(Model model, Authentication authentication) {
        String benhNhanId = getBenhNhanIdFromAuth(authentication);

        EditProfileRequest editProfileRequest = profileService.getEditProfileRequest(benhNhanId);
        model.addAttribute("pageTitle", "Sửa đổi thông tin cá nhân");
        model.addAttribute("editProfileRequest", editProfileRequest);
        return "normalUser/edit-profile";
    }

    // Sửa thông tin cá nhân - Submit cập nhật
    @PostMapping("/edit-profile")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('UPDATE_PROFILE') or hasRole('ADMIN')")
    public String updateProfile(
            @ModelAttribute("editProfileRequest") @Valid EditProfileRequest editProfileRequest,
            BindingResult bindingResult,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        String benhNhanId = getBenhNhanIdFromAuth(authentication);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Sửa đổi thông tin cá nhân");
            return "normalUser/edit-profile";
        }
        profileService.updateProfile(benhNhanId, editProfileRequest);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin cá nhân thành công!");
        return "redirect:/normalUser/view-profile";
    }

    /**
     * Hiển thị danh sách tình hình dịch bệnh có phân trang.
     *
     * @param model model để truyền dữ liệu sang view
     * @param page  số trang hiện tại (default = 0)
     * @param size  số bản ghi mỗi trang (default = 10)
     * @return tên file Thymeleaf view
     */
    @GetMapping("/epidemic")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('VIEW_EPIDEMIC') or hasRole('ADMIN')")
    public String showDiseaseReport(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<DichBenhResponse> dichBenhPage = dichBenhService.getAllActiveDichBenh(PageRequest.of(page, size));

        // Set số thứ tự cho từng bản ghi trong trang (STT)
        int startStt = page * size + 1;
        int idx = 0;
        for (DichBenhResponse response : dichBenhPage.getContent()) {
            response.setStt(startStt + idx);
            idx++;
        }

        model.addAttribute("pageTitle", "Tình hình dịch bệnh tại địa phương");
        model.addAttribute("dichBenhPage", dichBenhPage);

        return "normalUser/epidemic";
    }

    @GetMapping("/feedback-highlevel")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK') or hasRole('ADMIN')")
    public String showHighFeedbackForm(Model model) {
        model.addAttribute("pageTitle", "Phản hồi cấp cao");
        if (!model.containsAttribute("phanHoiCapCaoRequest")) {
            model.addAttribute("phanHoiCapCaoRequest", new PhanHoiCapCaoRequest());
        }
        model.addAttribute("dsLoaiPhanHoi", PhanHoiEntity.LoaiPhanHoi.values());
        return "normalUser/feedback-highlevel";
    }

    @PostMapping("/feedback-highlevel")
    @PreAuthorize("hasRole('NORMAL_USER') and hasAuthority('SUBMIT_FEEDBACK') or hasRole('ADMIN')")
    public String submitHighFeedback(
            @ModelAttribute("phanHoiCapCaoRequest") @Valid PhanHoiCapCaoRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("pageTitle", "Phản hồi cấp cao");
        model.addAttribute("dsLoaiPhanHoi", PhanHoiEntity.LoaiPhanHoi.values());
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

}