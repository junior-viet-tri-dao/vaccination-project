package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.normalUser.PhanHoiCapCaoRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        // Lấy username hiện tại từ security context (giả sử đã đăng nhập)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lấy thông tin bệnh nhân tương ứng với tài khoản đăng nhập và chưa xóa mềm
        BenhNhanEntity benhNhan = benhNhanRepository.findByTaoBoiTaiKhoan_TenDangNhapAndIsDeletedFalse(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân hoặc đã bị xóa"));

        // Lấy tài khoản admin đã tạo bệnh nhân này
        TaiKhoanEntity taiKhoanAdminTao = benhNhan.getTaoBoiTaiKhoan();

        PhanHoiEntity phanHoi = phanHoiMapper.toEntity(request);
        phanHoi.setIsDeleted(false);
        phanHoi.setNgayTao(LocalDateTime.now());
        phanHoi.setTrangThai(PhanHoiEntity.TrangThai.MOI);
        phanHoi.setBenhNhan(benhNhan);
        phanHoi.setTaoBoi(taiKhoanAdminTao); // Đặt creator là admin tạo bệnh nhân

        phanHoiRepository.save(phanHoi);
    }
}