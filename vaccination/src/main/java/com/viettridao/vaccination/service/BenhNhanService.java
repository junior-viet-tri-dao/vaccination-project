package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.employee.UpdateBenhNhanRequest;
import com.viettridao.vaccination.dto.response.employee.UpdateBenhNhanResponse;
import com.viettridao.vaccination.model.BenhNhanEntity;

public interface BenhNhanService {
	List<BenhNhanEntity> getAllPatients();

	UpdateBenhNhanResponse updateBenhNhan(UpdateBenhNhanRequest request);

	UpdateBenhNhanResponse getBenhNhanById(String maBenhNhan);
	
    List<UpdateBenhNhanResponse> getAllBenhNhan();
    
    List<BenhNhanEntity> getAll();

}
