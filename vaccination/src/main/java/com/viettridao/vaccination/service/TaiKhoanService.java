package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.adminPanel.TaiKhoanCreateRequest;
import com.viettridao.vaccination.model.TaiKhoanEntity;

public interface TaiKhoanService {
    List<TaiKhoanEntity> getAll();

    List<TaiKhoanEntity> getTatCaBacSiHoatDong();

    TaiKhoanEntity createTaiKhoan(TaiKhoanCreateRequest request);

    List<TaiKhoanEntity> getAllDoctors();
}
