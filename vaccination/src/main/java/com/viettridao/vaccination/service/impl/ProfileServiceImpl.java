package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.request.normalUser.EditProfileRequest;
import com.viettridao.vaccination.dto.response.normalUser.ProfileDetailResponse;
import com.viettridao.vaccination.mapper.ProfileMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.KetQuaTiemRepository;
import com.viettridao.vaccination.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final BenhNhanRepository benhNhanRepository;
    private final KetQuaTiemRepository ketQuaTiemRepository;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional(readOnly = true)
    public ProfileDetailResponse getProfileDetailByUsername(String tenDangNhap) {
        // Lấy bệnh nhân theo tài khoản (OneToOne)
        BenhNhanEntity benhNhan = benhNhanRepository.findByTaiKhoan_TenDangNhapAndIsDeletedFalse(tenDangNhap)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin bệnh nhân"));

        // Lấy lịch sử tiêm (chỉ lấy các kết quả chưa bị xóa)
        List<KetQuaTiemEntity> ketQuaTiemList = ketQuaTiemRepository
                .findByBenhNhan_IdAndIsDeletedFalseOrderByNgayTiemAsc(benhNhan.getId());

        // Map sang DTO
        ProfileDetailResponse response = profileMapper.toProfileDetail(benhNhan);
        response.setLichSuTiem(profileMapper.toLichSuTiemList(ketQuaTiemList));

        return response;
    }

    @Override
    @Transactional
    public void updateProfileByUsername(String tenDangNhap, EditProfileRequest request) {
        BenhNhanEntity benhNhan = benhNhanRepository.findByTaiKhoan_TenDangNhapAndIsDeletedFalse(tenDangNhap)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân"));

        // Cập nhật các trường thông tin cá nhân
        benhNhan.setHoTen(request.getTen());
        benhNhan.setNgaySinh(request.getNgaySinh());
        benhNhan.setGioiTinh(
                request.getGioiTinh() != null
                        ? BenhNhanEntity.GioiTinh.valueOf(request.getGioiTinh().name())
                        : null
        );
        benhNhan.setDiaChi(request.getDiaChi());

        benhNhanRepository.save(benhNhan);
    }

    @Override
    public EditProfileRequest getEditProfileRequestByUsername(String tenDangNhap) {
        BenhNhanEntity benhNhan = benhNhanRepository.findByTaiKhoan_TenDangNhapAndIsDeletedFalse(tenDangNhap)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân"));

        return EditProfileRequest.builder()
                .ten(benhNhan.getHoTen())
                .ngaySinh(benhNhan.getNgaySinh())
                .gioiTinh(
                        benhNhan.getGioiTinh() != null
                                ? EditProfileRequest.GioiTinh.valueOf(benhNhan.getGioiTinh().name())
                                : null
                )
                .diaChi(benhNhan.getDiaChi())
                .build();
    }
}