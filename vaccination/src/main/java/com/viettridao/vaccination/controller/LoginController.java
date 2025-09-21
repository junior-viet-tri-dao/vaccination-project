package com.viettridao.vaccination.controller;

import com.viettridao.vaccination.dto.request.LoginRequest;
import com.viettridao.vaccination.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AuthController
 * Controller xử lý các request liên quan đến đăng nhập cho hệ thống vaccination.
 * Quản lý hiển thị form đăng nhập và xử lý xác thực tài khoản.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService authService;

    /**
     * Hiển thị form đăng nhập.
     */
    @GetMapping("")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new LoginRequest());
        return "login"; // Trả về template login
    }

    /**
     * Xử lý logic đăng nhập.
     */
    @PostMapping("")
    public String login(
            @Valid @ModelAttribute("login") LoginRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                // Trả về lại login nếu validation lỗi
                return "login";
            }
            boolean result = authService.login(request.getUsername(), request.getPassword());
            if (result) {
                redirectAttributes.addFlashAttribute("success", "Đăng nhập thành công!");
                return "redirect:/admin/dashboard";
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không hợp lệ");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi, vui lòng thử lại sau");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("error", "Đăng nhập thất bại");
        return "redirect:/login";
    }
}