package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.employee.KeDonRequest;
import com.viettridao.vaccination.dto.response.employee.KeDonResponse;
import com.viettridao.vaccination.mapper.KeDonMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.DonThuocRepository;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.KeDonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeDonServiceImpl implements KeDonService {

	private final DonThuocRepository donThuocRepository;
	private final BenhNhanRepository benhNhanRepository;
	private final VacXinRepository vacXinRepository;
	private final KeDonMapper keDonMapper;

	@Override
	@Transactional
	public KeDonResponse keDon(KeDonRequest request, String userLogin) {
		// Map request -> entity
		DonThuocEntity entity = keDonMapper.toEntity(request);

		// Save DB
		DonThuocEntity saved = donThuocRepository.save(entity);

		// Map entity -> response
		return keDonMapper.toResponse(saved);
	}

	@Override
	public List<BenhNhanEntity> getAllBenhNhan() {
		return benhNhanRepository.findAll();
	}

	@Override
	public List<VacXinEntity> getAllVacXin() {
		return vacXinRepository.findAll();
	}
}
