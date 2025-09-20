package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.normalUser.EditProfileRequest;
import com.viettridao.vaccination.dto.response.normalUser.ProfileDetailResponse;

public interface ProfileService {
    /**
     * Lấy thông tin hồ sơ chi tiết của bệnh nhân theo ID tài khoản đăng nhập
     * @param benhNhanId mã bệnh nhân (lấy từ user context)
     * @return thông tin hồ sơ cá nhân và lịch sử tiêm chủng
     */
    ProfileDetailResponse getProfileDetail(String benhNhanId);

    /**
     * Cập nhật thông tin cá nhân của bệnh nhân
     * @param benhNhanId mã bệnh nhân (lấy từ user context)
     * @param request thông tin cập nhật
     */
    void updateProfile(String benhNhanId, EditProfileRequest request);

    EditProfileRequest getEditProfileRequest(String benhNhanId);
}