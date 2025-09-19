package com.viettridao.vaccination.service;

import com.viettridao.vaccination.model.QuyenHanEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    @Override
    public UserDetails loadUserByUsername(String tenDangNhap) throws UsernameNotFoundException {
        TaiKhoanEntity account = accountRepository.findByTenDangNhapWithRoleAndPermission(tenDangNhap)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản có username = " + tenDangNhap));

        Set<GrantedAuthority> authorities = new HashSet<>();
        // Gán quyền hạn
        if (account.getVaiTro().getQuyenHans() != null) {
            for (QuyenHanEntity permission : account.getVaiTro().getQuyenHans()) {
                authorities.add(new SimpleGrantedAuthority(permission.getTen()));
            }
        }

        return account;
    }
}