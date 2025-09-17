package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.supportemployee.NhacNhoTiemRequest;
import com.viettridao.vaccination.dto.response.supportemployee.NhacNhoTiemResponse;

public interface NhacNhoTiemService {

	List<NhacNhoTiemResponse> getLichSuVaDuKien(String maBenhNhan);

	void sendEmail(NhacNhoTiemRequest request);

	List<NhacNhoTiemResponse> getAllReminders();

}