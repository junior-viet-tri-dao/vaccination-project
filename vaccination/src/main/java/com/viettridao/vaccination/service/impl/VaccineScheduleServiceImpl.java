package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.dto.response.normalUser.VaccineScheduleResponse;
import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.DonThuocEntity;
import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.DonThuocRepository;
import com.viettridao.vaccination.repository.LichTiemRepository;
import com.viettridao.vaccination.service.VaccineScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Triển khai service cho lịch tiêm chủng cá nhân dựa trên đơn thuốc được bác sĩ kê.
 */
@Service
@RequiredArgsConstructor
public class VaccineScheduleServiceImpl implements VaccineScheduleService {

    private final DonThuocRepository donThuocRepository;
    private final LichTiemRepository lichTiemRepository;

    @Override
    public List<VaccineScheduleResponse> getVaccineSchedulesForUser(String userId) {
        List<DonThuocEntity> donThuocList = donThuocRepository.findByBenhNhanId(userId)
                .stream()
                .filter(don -> Boolean.FALSE.equals(don.getIsDeleted()))
                .sorted(Comparator.comparing(DonThuocEntity::getHenTiemLai, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        AtomicInteger sttCounter = new AtomicInteger(1);

        return donThuocList.stream()
                .map(don -> {
                    VaccineScheduleResponse dto = new VaccineScheduleResponse();
                    dto.setStt(sttCounter.getAndIncrement());
                    dto.setNgayTiem(don.getHenTiemLai() != null ? don.getHenTiemLai().toLocalDate() : null);
                    dto.setThoiGian(don.getHenTiemLai() != null ? don.getHenTiemLai().toLocalTime() : null);

                    // Lấy địa điểm từ lịch tiêm chủng (theo mã vắc xin và ngày giờ)
                    String diaDiem = "";
                    if (don.getVacXin() != null && don.getHenTiemLai() != null) {
                        List<LichTiemEntity> lichTiemList = lichTiemRepository.findByVacXinIdAndNgayGio(
                                don.getVacXin().getId(),
                                don.getHenTiemLai()
                        );
                        if (!lichTiemList.isEmpty()) {
                            // Nếu có nhiều lịch, lấy bản đầu tiên hoặc xử lý theo nhu cầu thực tế
                            diaDiem = lichTiemList.get(0).getDiaDiem();
                        }
                    }
                    dto.setDiaDiem(diaDiem);

                    dto.setTenVacXin(don.getVacXin() != null ? don.getVacXin().getTen() : "");
                    dto.setLoaiVacXin(don.getVacXin() != null ? don.getVacXin().getLoai() : "");
                    dto.setSoLuong(1); // Thường mỗi đơn là 1 mũi, hoặc lấy từ trường khác nếu có
                    dto.setDoiTuong(don.getVacXin() != null ? don.getVacXin().getDoiTuongTiem() : "");
                    dto.setGhiChu(getGhiChuGiaVacXin(don));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Lấy ghi chú giá vắc xin từ bảng giá (lấy giá còn hiệu lực hoặc gần nhất).
     * - Nếu miễn phí, trả về "Miễn phí"
     * - Nếu có giá, trả về giá dạng "120.000đ/mũi"
     */
    private String getGhiChuGiaVacXin(DonThuocEntity don) {
        VacXinEntity vacXin = don.getVacXin();
        if (vacXin == null || vacXin.getBangGiaVacXins() == null || vacXin.getBangGiaVacXins().isEmpty()) {
            return "";
        }
        LocalDate ngayTiem = don.getHenTiemLai() != null ? don.getHenTiemLai().toLocalDate() : LocalDate.now();
        Optional<BangGiaVacXinEntity> giaHienTai = vacXin.getBangGiaVacXins().stream()
                .filter(bg -> (bg.getHieuLucTu() == null || !ngayTiem.isBefore(bg.getHieuLucTu()))
                        && (bg.getHieuLucDen() == null || !ngayTiem.isAfter(bg.getHieuLucDen())))
                .sorted((a, b) -> {
                    LocalDate aTu = a.getHieuLucTu() == null ? LocalDate.MIN : a.getHieuLucTu();
                    LocalDate bTu = b.getHieuLucTu() == null ? LocalDate.MIN : b.getHieuLucTu();
                    return bTu.compareTo(aTu); // giảm dần, lấy mới nhất
                })
                .findFirst();

        if (giaHienTai.isPresent()) {
            Integer gia = giaHienTai.get().getGia();
            if (gia == null || gia == 0) return "Miễn phí";
            return String.format("%,d đ/mũi", gia);
        }
        return "";
    }
}