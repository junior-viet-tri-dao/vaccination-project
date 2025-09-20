package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.repository.LichTiemRepository;
import com.viettridao.vaccination.service.LichTiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LichTiemServiceImpl implements LichTiemService {

	private final LichTiemRepository lichTiemRepository;

	@Override
	public List<LichTiemEntity> getAllLichTiem() {
		return lichTiemRepository.findAll();
	}
	

	@Override
    public List<LichTiemEntity> getAll() {
        return lichTiemRepository.findAll();
    }

}
