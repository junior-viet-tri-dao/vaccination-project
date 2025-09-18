package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.response.DichBenhResponse;
import com.viettridao.vaccination.mapper.DichBenhMapper;
import com.viettridao.vaccination.model.DichBenhEntity;
import com.viettridao.vaccination.repository.DichBenhRepository;
import com.viettridao.vaccination.service.DichBenhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DichBenhServiceImpl implements DichBenhService {

    private final DichBenhRepository dichBenhRepository;
    private final DichBenhMapper dichBenhMapper;

    @Override
    public Page<DichBenhResponse> getAllActiveDichBenh(Pageable pageable) {
        Page<DichBenhEntity> entityPage = dichBenhRepository.findActiveOrderByThoiDiemKhaoSatDesc(pageable);
        // MapStruct không tự map Page, nên cần map thủ công từng phần tử
        return entityPage.map(entity -> dichBenhMapper.toResponse(entity));
    }
}