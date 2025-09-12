package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.model.BienDongKhoEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.repository.BienDongKhoRepository;
import com.viettridao.vaccination.service.BienDongKhoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BienDongKhoServiceImpl implements BienDongKhoService {

	private final BienDongKhoRepository repository;

	@Override
	@Transactional
	public void softDeleteByLoVacXin(LoVacXinEntity loVacXin) {
		List<BienDongKhoEntity> list = repository.findAllByLoVacXinAndIsDeletedFalse(loVacXin);
		list.forEach(bd -> bd.setIsDeleted(true));
		repository.saveAll(list);
	}

}
