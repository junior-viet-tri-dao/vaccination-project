package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.normalUser.EditProfileRequest;
import com.viettridao.vaccination.dto.response.normalUser.ProfileDetailResponse;

public interface ProfileService {
    /**
     * Lấy thông tin hồ sơ chi tiết của bệnh nhân theo tên đăng nhập tài khoản
     *
     * @param tenDangNhap tên đăng nhập (lấy từ user context)
     * @return thông tin hồ sơ cá nhân và lịch sử tiêm chủng
     */
    ProfileDetailResponse getProfileDetailByUsername(String tenDangNhap);

    /**
     * Cập nhật thông tin cá nhân của bệnh nhân theo tên đăng nhập tài khoản
     *
     * @param tenDangNhap tên đăng nhập (lấy từ user context)
     * @param request     thông tin cập nhật
     */
    void updateProfileByUsername(String tenDangNhap, EditProfileRequest request);

    /**
     * Lấy thông tin để hiển thị form chỉnh sửa theo tên đăng nhập tài khoản
     */
    EditProfileRequest getEditProfileRequestByUsername(String tenDangNhap);
}