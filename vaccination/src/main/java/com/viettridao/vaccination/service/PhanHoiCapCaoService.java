package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiCapCaoRequest;

import java.util.List;

public interface PhanHoiCapCaoService {
    /**
     * Gửi phản hồi cấp cao cho bệnh nhân hiện tại (lấy theo tài khoản đăng nhập).
     *
     * @param request dữ liệu phản hồi từ form
     */
    void guiPhanHoiCapCao(PhanHoiCapCaoRequest request);

    List<com.viettridao.vaccination.dto.response.PhanHoiAdminResponse> getAllForAdmin();

}
