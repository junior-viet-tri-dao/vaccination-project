package com.viettridao.vaccination.service;

import com.viettridao.vaccination.model.LoVacXinEntity;

public interface ChiTietHdNccService {

	/**
	 * Xóa tất cả chi tiết hóa đơn liên quan đến lô vắc xin
	 * 
	 * @param loVacXin lô vắc xin
	 */
	 // Xóa mềm theo lô vắc xin
    void softDeleteByLoVacXin(LoVacXinEntity loVacXin);

    
}
