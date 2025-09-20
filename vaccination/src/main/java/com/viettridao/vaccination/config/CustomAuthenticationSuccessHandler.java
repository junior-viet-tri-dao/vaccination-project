package com.viettridao.vaccination.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		String username = authentication.getName(); 
		String redirectUrl = "/dashboard"; 

		switch (username) {
		case "admin1":
			redirectUrl = "/admin/dashboard";
			break;
		case "doctor1":
			redirectUrl = "/doctor/dashboard";
			break;
		case "warehouse1":
			redirectUrl = "/warehouse/dashboard";
			break;
		case "supporter1":
			redirectUrl = "/support/dashboard";
			break;
		case "finance1":
			redirectUrl = "/finance/dashboard";
			break;
		case "user1":
			redirectUrl = "/user/dashboard";
			break;
		default:
			redirectUrl = "/access-denied";
			break;
		}

		response.sendRedirect(redirectUrl);
	}
}
