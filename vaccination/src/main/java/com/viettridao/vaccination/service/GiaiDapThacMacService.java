package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.dto.request.supportemployee.GiaiDapThacMacRequest;
import com.viettridao.vaccination.dto.response.supportemployee.GiaiDapThacMacResponse;

public interface GiaiDapThacMacService {
    GiaiDapThacMacResponse getByMaPh(String maPh);
    void traLoi(GiaiDapThacMacRequest request);
    List<GiaiDapThacMacResponse> getAll();

}
