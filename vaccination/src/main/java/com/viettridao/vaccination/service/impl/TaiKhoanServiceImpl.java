package com.viettridao.vaccination.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.viettridao.vaccination.dto.request.adminPanel.TaiKhoanCreateRequest;
import com.viettridao.vaccination.mapper.BenhNhanMapper;
import com.viettridao.vaccination.mapper.TaiKhoanMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.VaiTroEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.VaiTroRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.service.TaiKhoanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaiKhoanServiceImpl implements TaiKhoanService {
    private final TaiKhoanRepository taiKhoanRepository;
    private final VaiTroRepository vaiTroRepository;
    private final TaiKhoanMapper taiKhoanMapper;
    private final PasswordEncoder passwordEncoder;
    private final BenhNhanRepository benhNhanRepository;
    private final BenhNhanMapper benhNhanMapper;

    @Override
    @Transactional
    public TaiKhoanEntity createTaiKhoan(TaiKhoanCreateRequest request) {
        // Kiểm tra username đã tồn tại
        if (taiKhoanRepository.existsByTenDangNhapAndIsDeletedFalse(request.getTenDangNhap())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }

        // Map request sang entity
        TaiKhoanEntity entity = new TaiKhoanEntity();
        entity.setTenDangNhap(request.getTenDangNhap());
        entity.setMatKhauHash(passwordEncoder.encode(request.getMatKhauRaw()));
        entity.setHoTen(request.getHoTen());
        entity.setSoCmnd(request.getSoCmnd());
        entity.setDiaChi(request.getDiaChi());
        entity.setDescription(request.getDescription());
        entity.setIsDeleted(Boolean.FALSE);
        entity.setNgayTao(LocalDateTime.now());
        entity.setNgayCapNhat(LocalDateTime.now());

        // Gán vai trò (lấy vai trò đầu tiên, hoặc xử lý đa vai trò nếu cần)
        List<VaiTroEntity> vaiTros = vaiTroRepository.findAllByIdInAndIsDeletedFalse(request.getVaiTroIds());
        if (vaiTros.isEmpty()) throw new IllegalArgumentException("Vai trò không hợp lệ");
        entity.setVaiTro(vaiTros.get(0));

        // Lưu tài khoản trước để lấy id
        TaiKhoanEntity savedTaiKhoan = taiKhoanRepository.save(entity);

        // Nếu là NORMAL_USER thì tạo thêm bản ghi bệnh nhân
        if ("NORMAL_USER".equalsIgnoreCase(savedTaiKhoan.getVaiTro().getTen())) {
            // Map trực tiếp từ TaiKhoanEntity sang BenhNhanEntity bằng mapper
            BenhNhanEntity benhNhan = benhNhanMapper.fromTaiKhoan(savedTaiKhoan);

            // Bổ sung các field còn lại
            benhNhan.setNgayTao(LocalDateTime.now());
            benhNhan.setIsDeleted(Boolean.FALSE);
            benhNhan.setTaiKhoan(savedTaiKhoan);

            // Gán tài khoản admin hiện tại làm taoBoiTaiKhoan
            String adminUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            TaiKhoanEntity admin = taiKhoanRepository.findByTenDangNhapAndIsDeletedFalse(adminUsername)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản admin"));
            benhNhan.setTaoBoiTaiKhoan(admin);

            // Lưu bệnh nhân
            benhNhanRepository.save(benhNhan);
        }

        return savedTaiKhoan;
    }

    @Override
    public List<TaiKhoanEntity> getAll() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public List<TaiKhoanEntity> getTatCaBacSiHoatDong() {
        return taiKhoanRepository.findByVaiTro_TenAndHoatDongTrue("DOCTER");
    }

    @Override
    public List<TaiKhoanEntity> getAllDoctors() {
        return taiKhoanRepository.findByVaiTro_TenAndVaiTroIsDeletedFalse("DOCTER");
    }
}
