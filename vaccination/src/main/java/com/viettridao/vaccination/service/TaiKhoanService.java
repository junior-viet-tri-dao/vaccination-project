package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.model.TaiKhoanEntity;

public interface TaiKhoanService {
	List<TaiKhoanEntity> getAll();
	
    List<TaiKhoanEntity> getTatCaBacSiHoatDong();

}
