package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.repository.LoVacXinRepository;
import com.viettridao.vaccination.service.LoVacXinService;

@Service
public class LoVacXinServiceImpl implements LoVacXinService {

	private final LoVacXinRepository repository;

	// Constructor injection
	public LoVacXinServiceImpl(LoVacXinRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<LoVacXinEntity> findByVacXinMaCode(String maCode) {
		return repository.findByVacXinMaCode(maCode);
	}

	@Override
	public LoVacXinEntity save(LoVacXinEntity loVacXin) {
		return repository.save(loVacXin);
	}

	@Override
	public void softDelete(LoVacXinEntity loVacXin) {
		// Giả sử có trường isDeleted trong entity
		loVacXin.setIsDeleted(true);
		repository.save(loVacXin);
	}

	@Override
	public List<LoVacXinEntity> findAll() {
		return repository.findAllByIsDeletedFalse();
	}
}
