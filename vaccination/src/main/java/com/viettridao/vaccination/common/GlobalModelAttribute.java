package com.viettridao.vaccination.common;

import com.viettridao.vaccination.model.AccountEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {
    @ModelAttribute("user")
    public AccountEntity addUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return (AccountEntity) auth.getPrincipal();
        }
        return null;
    }
}
