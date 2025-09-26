package com.viettridao.vaccination.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiCapCaoRequest;
import com.viettridao.vaccination.dto.response.PhanHoiAdminResponse;
import com.viettridao.vaccination.mapper.PhanHoiCapCaoMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.PhanHoiEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.PhanHoiCapCaoRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.service.PhanHoiCapCaoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhanHoiCapCaoServiceImpl implements PhanHoiCapCaoService {

    private final PhanHoiCapCaoRepository phanHoiRepository;
    private final BenhNhanRepository benhNhanRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final PhanHoiCapCaoMapper phanHoiMapper;

    @Override
    @Transactional
    public void guiPhanHoiCapCao(PhanHoiCapCaoRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Tìm bệnh nhân theo tài khoản đăng nhập
        BenhNhanEntity benhNhan = benhNhanRepository.findByTaiKhoan_TenDangNhapAndIsDeletedFalse(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân hoặc đã bị xóa"));

        TaiKhoanEntity taiKhoanBenhNhan = benhNhan.getTaiKhoan();

        PhanHoiEntity phanHoi = phanHoiMapper.toEntity(request);
        phanHoi.setIsDeleted(false);
        phanHoi.setNgayTao(LocalDateTime.now());
        phanHoi.setTrangThai(PhanHoiEntity.TrangThai.MOI);
        phanHoi.setBenhNhan(benhNhan);
        phanHoi.setTaoBoi(taiKhoanBenhNhan);

        phanHoiRepository.save(phanHoi);
    }

    @Override
    public List<PhanHoiAdminResponse> getAllForAdmin() {
        List<PhanHoiEntity> entities = phanHoiRepository.findAllByIsDeletedFalseOrderByNgayTaoDesc();
        return phanHoiMapper.toAdminResponseList(entities);
    }
}