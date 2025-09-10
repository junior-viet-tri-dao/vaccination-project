package com.viettridao.vaccination.service;

import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserServiceDetail
 * Service chi tiết người dùng (UserServiceDetail).
 * Chịu trách nhiệm xử lý logic nghiệp vụ liên quan đến xác thực và tải thông tin người dùng.
 */
@Service
@RequiredArgsConstructor
@Getter
public class UserServiceDetail implements UserDetailsService {

    // Repository cho thực thể AccountEntity
    private final TaiKhoanRepository accountRepository;

    /**
     * Tải thông tin người dùng dựa trên tên đăng nhập.
     *
     * @param username Tên đăng nhập của người dùng.
     * @return Đối tượng UserDetails chứa thông tin người dùng.
     * @throws UsernameNotFoundException Nếu không tìm thấy người dùng với tên đăng nhập đã cung cấp.
     */
    @Override
    public UserDetails loadUserByUsername(String tenDangNhap) throws UsernameNotFoundException {
        TaiKhoanEntity account = accountRepository.findByUsername(tenDangNhap)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy tài khoản có username = " + tenDangNhap));

        return User.withUsername(account.getTenDangNhap())   // ✅ getter đúng
                .password(account.getMatKhauHash())          // BCrypt hash
                .roles(account.getVaiTro().getTen())         // tên vai trò, ví dụ: ADMIN, USER
                .build();
    }
}