package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.model.ChiTietHDNCCEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.repository.ChiTietHdNccRepository;
import com.viettridao.vaccination.service.ChiTietHdNccService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChiTietHdNccServiceImpl implements ChiTietHdNccService {

	private final ChiTietHdNccRepository repository;

	@Override
	@Transactional
	public void softDeleteByLoVacXin(LoVacXinEntity loVacXin) {
		List<ChiTietHDNCCEntity> list = repository.findAllByLoVacXinAndIsDeletedFalse(loVacXin);
		list.forEach(ct -> ct.setIsDeleted(true));
		repository.saveAll(list);
	}

}
