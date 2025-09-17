package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.response.employee.HoSoBenhAnResponse;
import com.viettridao.vaccination.mapper.HoSoBenhAnMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.DangKyTiemEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.DangKyTiemRepository;
import com.viettridao.vaccination.repository.KetQuaTiemRepository;
import com.viettridao.vaccination.service.HoSoBenhAnService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoSoBenhAnServiceImpl implements HoSoBenhAnService {

	private final BenhNhanRepository benhNhanRepository;
	private final KetQuaTiemRepository ketQuaTiemRepository;
	private final DangKyTiemRepository dangKyTiemRepository;
	private final HoSoBenhAnMapper hoSoBenhAnMapper;

	@Override
	@Transactional(readOnly = true)
	public HoSoBenhAnResponse getHoSoBenhAnById(String maBenhNhan) {
		BenhNhanEntity benhNhan = benhNhanRepository.findById(maBenhNhan)
				.orElseThrow(() -> new RuntimeException("Bệnh nhân không tồn tại"));

		List<KetQuaTiemEntity> ketQuaTiems = ketQuaTiemRepository.findByBenhNhanIdAndIsDeletedFalse(maBenhNhan);
		List<DangKyTiemEntity> dangKyTiems = dangKyTiemRepository.findByBenhNhanIdAndIsDeletedFalse(maBenhNhan);

		return hoSoBenhAnMapper.toResponse(benhNhan, ketQuaTiems, dangKyTiems);
	}
}
