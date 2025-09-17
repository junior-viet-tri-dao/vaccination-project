package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.employee.KeDonRequest;
import com.viettridao.vaccination.dto.response.employee.KeDonResponse;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.VacXinEntity;

public interface KeDonService {
	KeDonResponse keDon(KeDonRequest request, String userLogin);

	List<BenhNhanEntity> getAllBenhNhan();

	List<VacXinEntity> getAllVacXin();
}
