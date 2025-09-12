package com.viettridao.vaccination.service;

import com.viettridao.vaccination.model.LoVacXinEntity;

public interface BienDongKhoService {

	// Xóa mềm theo lô vắc xin
    void softDeleteByLoVacXin(LoVacXinEntity loVacXin);
	
}
