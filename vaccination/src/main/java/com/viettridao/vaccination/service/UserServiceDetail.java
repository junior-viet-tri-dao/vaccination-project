package com.viettridao.vaccination.service;

import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    /**
     * Tải thông tin người dùng dựa trên tên đăng nhập.
     *
     * @param username Tên đăng nhập của người dùng.
     * @return Đối tượng UserDetails chứa thông tin người dùng.
     * @throws UsernameNotFoundException Nếu không tìm thấy người dùng với tên đăng nhập đã cung cấp.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoanEntity account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy tài khoản có username = " + username));

        // Trả về UserDetails mặc định của Spring Security
        return User.withUsername(account.getUsername())
                .password(account.getMatKhauHash()) // BCrypt password hash
                .roles(account.getVaiTro().getTen()) // Vai trò từ entity VaiTroEntity
                .build();
    }
}