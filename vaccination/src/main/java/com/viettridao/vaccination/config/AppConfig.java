package com.viettridao.vaccination.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.DispatcherServlet;

import com.viettridao.vaccination.service.UserServiceDetail;

import lombok.RequiredArgsConstructor;

/**
 * AppConfig Lớp cấu hình bảo mật và các bean quan trọng cho ứng dụng.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class AppConfig {

	// Danh sách các endpoint cho phép truy cập mà không cần đăng nhập
	private static final String[] AUTH_WHITELIST = { "/login", "/js/**", "/css/**", "/images/**" };

	// Service dùng để load user khi xác thực
	private final UserServiceDetail userServiceDetail;

	/**
	 * Cấu hình bảo mật chính cho ứng dụng.
	 *
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(Customizer.withDefaults())
//                .cors(withDefaults())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .authenticationProvider(authenticationProvider())
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler successHandler)
			throws Exception {

		http.csrf(AbstractHttpConfigurer::disable).cors(withDefaults())
				.authorizeHttpRequests(
						request -> request.requestMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler)
						.permitAll())
				.exceptionHandling(ex -> ex.accessDeniedPage("/403") // hoặc redirect đến URL khác
				)
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll())
				.authenticationProvider(authenticationProvider());

		return http.build();
	}

	/**
	 * Provider xác thực với userServiceDetail và passwordEncoder.
	 *
	 * @return AuthenticationProvider
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userServiceDetail);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/**
	 * Quản lý xác thực cho Spring Security.
	 *
	 * @param config AuthenticationConfiguration
	 * @return AuthenticationManager
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Mã hóa mật khẩu bằng BCrypt.
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration(
			DispatcherServlet dispatcherServlet) {
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return new ServletRegistrationBean<>(dispatcherServlet, "/");
	}

	@Bean
	public DispatcherServletPath dispatcherServletPath() {
		return new DispatcherServletPath() {
			@Override
			public String getPath() {
				return "/";
			}
		};
	}
}
