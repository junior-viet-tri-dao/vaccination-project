package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.service.TaiKhoanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaiKhoanServiceImpl implements TaiKhoanService {

	private final TaiKhoanRepository taiKhoanRepository;

	@Override
	public List<TaiKhoanEntity> getAll() {
		return taiKhoanRepository.findAll();
	}
	
	 @Override
	    public List<TaiKhoanEntity> getAllDoctors() {
	        return taiKhoanRepository.findByVaiTro_TenAndVaiTroIsDeletedFalse("DOCTER");
	    }
}
