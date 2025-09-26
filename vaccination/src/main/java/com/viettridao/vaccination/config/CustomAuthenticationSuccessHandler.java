package com.viettridao.vaccination.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println("Authorities after login for user '" + authentication.getName() + "':");
        authorities.forEach(a -> System.out.println(" - " + a.getAuthority()));

        String redirectUrl = "/access-denied";

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/adminpanel/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTER"))) {
            redirectUrl = "/employee/view";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_WAREHOUSE"))) {
            redirectUrl = "/warehouse";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPPORTER"))) {
            redirectUrl = "/support/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_FINANCE"))) {
            redirectUrl = "/finance/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_NORMAL_USER"))) {
            redirectUrl = "/normalUser/dashboard";
        }

        System.out.println("Redirecting user '" + authentication.getName() + "' to: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
