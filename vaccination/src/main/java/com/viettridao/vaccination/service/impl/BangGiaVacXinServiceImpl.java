package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BangGiaVacXinRepository;
import com.viettridao.vaccination.service.BangGiaVacXinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BangGiaVacXinServiceImpl implements BangGiaVacXinService {

	private final BangGiaVacXinRepository repository;

	@Override
	@Transactional
	public void softDeleteByVacXin(VacXinEntity vacXin) {
		List<BangGiaVacXinEntity> list = repository.findByVacXinIdOrderByHieuLucTuDesc(vacXin.getId());
		list.forEach(bg -> bg.setIsDeleted(true));
		repository.saveAll(list);
	}

	@Override
	public List<BangGiaVacXinEntity> findByVacXinIdOrderByHieuLucTuDesc(String vacXinId) {
		return repository.findByVacXinIdOrderByHieuLucTuDesc(vacXinId);
	}

	@Override
	public BangGiaVacXinEntity save(BangGiaVacXinEntity bangGiaVacXin) {
		return repository.save(bangGiaVacXin);
	}
}
