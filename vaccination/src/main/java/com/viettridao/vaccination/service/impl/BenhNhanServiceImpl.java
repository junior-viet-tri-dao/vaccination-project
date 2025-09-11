package com.viettridao.vaccination.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.service.BenhNhanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BenhNhanServiceImpl implements BenhNhanService {
    private final BenhNhanRepository benhNhanRepository;

    @Override
    public List<BenhNhanEntity> getAllPatients() {
        return benhNhanRepository.findAll();
    }
}
