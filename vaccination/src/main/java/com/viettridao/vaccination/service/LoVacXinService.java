package com.viettridao.vaccination.service;

import java.util.List;
import java.util.Optional;

import com.viettridao.vaccination.model.LoVacXinEntity;

public interface LoVacXinService {

	Optional<LoVacXinEntity> findByVacXinMaCode(String maCode);

	LoVacXinEntity save(LoVacXinEntity loVacXin);

	void softDelete(LoVacXinEntity loVacXin);

	List<LoVacXinEntity> findAll();

}
