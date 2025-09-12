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
	public List<VacXinEntity> getAllVaccines() {
	    return vacXinRepository.findAllByIsDeletedFalse();
	}
	
	 @Override
	    public List<VacXinEntity> getAllActiveVaccines() {
	        return vacXinRepository.findAllByIsDeletedFalse();
	    }
}
