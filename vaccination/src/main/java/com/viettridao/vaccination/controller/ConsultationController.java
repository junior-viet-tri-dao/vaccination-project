package com.viettridao.vaccination.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.viettridao.vaccination.dto.response.supportemployee.CauHoiTiemChungResponse;
import com.viettridao.vaccination.service.CauHoiTiemChungService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final CauHoiTiemChungService cauHoiService;

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
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('READ_CONSULTATION')) or hasRole('ADMIN')")
    public String showConsultationForm(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Tư vấn tiêm chủng");
        model.addAttribute("cauHois", cauHoiService.getAllCauHoi());
        model.addAttribute("userRole", getUserRole(authentication));
        return "supportEmployee/consultation-form";
    }

    @GetMapping("/{maCauHoi}")
    @ResponseBody
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('READ_CONSULTATION')) or hasRole('ADMIN')")
    public CauHoiTiemChungResponse getCauHoiDetail(@PathVariable String maCauHoi) {
        return cauHoiService.getCauHoiById(maCauHoi);
    }

    @PostMapping("/create")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('CREATE_CONSULTATION')) or hasRole('ADMIN')")
    public String createCauHoi(@RequestParam String tieuDe, @RequestParam String noiDung) {
        cauHoiService.createCauHoi(tieuDe, noiDung);
        return "redirect:/consultation";
    }

    @GetMapping("/create-form")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('CREATE_CONSULTATION')) or hasRole('ADMIN')")
    public String showCreateForm(Model model, Authentication authentication) {
        model.addAttribute("userRole", getUserRole(authentication));
        return "supportEmployee/create-consultation-form";
    }

    @GetMapping("/edit-form/{maCauHoi}")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('UPDATE_CONSULTATION')) or hasRole('ADMIN')")
    public String showEditForm(@PathVariable String maCauHoi, Model model, Authentication authentication) {
        model.addAttribute("cauHoi", cauHoiService.getCauHoiById(maCauHoi));
        model.addAttribute("userRole", getUserRole(authentication));
        return "supportEmployee/edit-consultation-form";
    }

    @PostMapping("/update/{maCauHoi}")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('UPDATE_CONSULTATION')) or hasRole('ADMIN')")
    public String updateCauHoi(@PathVariable String maCauHoi, @RequestParam String tieuDe,
                               @RequestParam String noiDung) {
        cauHoiService.updateCauHoi(maCauHoi, tieuDe, noiDung);
        return "redirect:/consultation";
    }

    @PostMapping("/delete/{maCauHoi}")
    @PreAuthorize("(hasRole('SUPPORTER') and hasAuthority('DELETE_CONSULTATION')) or hasRole('ADMIN')")
    public String deleteCauHoi(@PathVariable String maCauHoi) {
        cauHoiService.deleteCauHoi(maCauHoi);
        return "redirect:/consultation";
    }
}