package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.service.VacXinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacXinServiceImpl implements VacXinService {

	private final VacXinRepository vacXinRepository;

	@Override
	public List<VacXinEntity> findAll() {
		return vacXinRepository.findAll();
	}

	@Override
	public List<VacXinEntity> getAllActiveVaccines() {
		return vacXinRepository.findByIsDeletedFalse(); // cần repository support
	}

	@Override
	public List<VacXinEntity> getAllVaccines() {
		return vacXinRepository.findAll(); // hoặc thêm logic riêng nếu cần
	}
	
	@Override
	public List<VacXinEntity> getAllVacXin() {
	    return vacXinRepository.findAll(); // hoặc findByIsDeletedFalse() nếu chỉ lấy vacxin còn hiệu lực
	}

}
