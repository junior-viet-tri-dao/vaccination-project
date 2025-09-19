package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.LoginRequest;
import com.viettridao.vaccination.model.TaiKhoanEntity;

public interface LoginService {
    /**
     * Xác thực thông tin đăng nhập của người dùng.
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return true nếu thành công, throw exception nếu sai
     */
    boolean login(String username, String password);
}
