package com.viettridao.vaccination.common;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.viettridao.vaccination.model.TaiKhoanEntity;

@ControllerAdvice
public class GlobalModelAttribute {
    @ModelAttribute("user")
    public TaiKhoanEntity addUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return (TaiKhoanEntity) auth.getPrincipal();
        }
        return null;
    }
}
