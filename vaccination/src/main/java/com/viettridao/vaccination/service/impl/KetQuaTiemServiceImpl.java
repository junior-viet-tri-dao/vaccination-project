package com.viettridao.vaccination.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.employee.KetQuaTiemRequest;
import com.viettridao.vaccination.dto.response.employee.KetQuaTiemResponse;
import com.viettridao.vaccination.mapper.KetQuaTiemMapper;
import com.viettridao.vaccination.model.BenhNhanEntity;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.repository.BenhNhanRepository;
import com.viettridao.vaccination.repository.KetQuaTiemRepository;
import com.viettridao.vaccination.repository.LichTiemRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.service.KetQuaTiemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KetQuaTiemServiceImpl implements KetQuaTiemService {

    private final KetQuaTiemRepository ketQuaTiemRepository;
    private final KetQuaTiemMapper ketQuaTiemMapper;
    private final BenhNhanRepository benhNhanRepository;
    private final LichTiemRepository lichTiemRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    public KetQuaTiemResponse getKetQuaTiemById(String id) {
        KetQuaTiemEntity entity = ketQuaTiemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kết quả tiêm không tồn tại"));
        return ketQuaTiemMapper.toResponse(entity);
    }

    @Override
    public List<KetQuaTiemResponse> getAllKetQuaTiem() {
        return ketQuaTiemRepository.findByIsDeletedFalse().stream().map(ketQuaTiemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<KetQuaTiemResponse> getKetQuaTiemByBenhNhan(String maBenhNhan) {
        return ketQuaTiemRepository.findByBenhNhanId(maBenhNhan).stream().map(ketQuaTiemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteKetQuaTiem(String id) {
        KetQuaTiemEntity entity = ketQuaTiemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kết quả tiêm không tồn tại"));
        entity.setIsDeleted(true);
        ketQuaTiemRepository.save(entity);
    }

    @Override
    public KetQuaTiemResponse getKetQuaTiemByTenBenhNhan(String tenBenhNhan) {
        Optional<KetQuaTiemEntity> optionalKq = ketQuaTiemRepository
                .findTopByBenhNhan_HoTenOrderByNgayTiemDesc(tenBenhNhan); // dùng path qua entity liên quan

        if (optionalKq.isPresent()) {
            KetQuaTiemEntity kq = optionalKq.get();
            return KetQuaTiemResponse.builder()
                    .maKq(kq.getId()) // id entity → maKq DTO
                    .tenBenhNhan(kq.getBenhNhan() != null ? kq.getBenhNhan().getHoTen() : null)
                    .tenVacXin(kq.getLichTiem() != null && kq.getLichTiem().getVacXin() != null
                            ? kq.getLichTiem().getVacXin().getTen() : null)
                    .nguoiThucHien(kq.getNguoiThucHien() != null ? kq.getNguoiThucHien().getHoTen() : null)
                    .ngayTiem(kq.getNgayTiem())
                    .tinhTrang(kq.getTinhTrang() != null ? kq.getTinhTrang().name() : null)
                    .phanUngSauTiem(kq.getPhanUngSauTiem())
                    .ghiChu(kq.getGhiChu())
                    .build();
        } else {
            return null;
        }
    }


    @Override
    public KetQuaTiemResponse createKetQuaTiem(KetQuaTiemRequest request) {
        // Map thủ công Request → Entity
        KetQuaTiemEntity entity = new KetQuaTiemEntity();
        entity.setNgayTiem(request.getNgayTiem());
        entity.setTinhTrang(KetQuaTiemEntity.TinhTrangTinhTrang.valueOf(request.getTinhTrang()));
        entity.setPhanUngSauTiem(request.getPhanUngSauTiem());
        entity.setGhiChu(request.getGhiChu());

        BenhNhanEntity benhNhan = benhNhanRepository.findByHoTen(request.getTenBenhNhan())
                .orElseThrow(() -> new RuntimeException("Bệnh nhân không tồn tại"));
        entity.setBenhNhan(benhNhan);

        LichTiemEntity lichTiem = lichTiemRepository.findByVacXinTen(request.getTenVacXin())
                .orElseThrow(() -> new RuntimeException("Lịch tiêm không tồn tại"));
        entity.setLichTiem(lichTiem);

        TaiKhoanEntity nguoiThucHien = taiKhoanRepository.findByHoTen(request.getNguoiThucHien())
                .orElseThrow(() -> new RuntimeException("Người thực hiện không tồn tại"));
        entity.setNguoiThucHien(nguoiThucHien);


        // Lưu entity
        KetQuaTiemEntity saved = ketQuaTiemRepository.save(entity);

        // Chuyển sang Response để trả về
        return ketQuaTiemMapper.requestToResponse(request); // hoặc toResponse(saved)
    }

    @Override
    public List<KetQuaTiemResponse> getKetQuaTiemHoanThanhByBenhNhan(String maBenhNhan) {
        return ketQuaTiemRepository
                .findByBenhNhanIdAndTinhTrang(maBenhNhan, KetQuaTiemEntity.TinhTrangTinhTrang.HOAN_THANH)
                .stream()
                .map(ketQuaTiemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public KetQuaTiemResponse getById(String id) {
        KetQuaTiemEntity entity = ketQuaTiemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả tiêm"));
        return ketQuaTiemMapper.toResponse(entity);
    }
}
