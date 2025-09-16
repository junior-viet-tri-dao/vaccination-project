package com.viettridao.vaccination.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.vaccination.dto.request.warehouse.ExportRequest;
import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.HoaDonChuaNhapResponse;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.mapper.WarehouseMapper;
import com.viettridao.vaccination.model.BienDongKhoEntity;
import com.viettridao.vaccination.model.ChiTietHDNCCEntity;
import com.viettridao.vaccination.model.HoaDonNCCEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.NhaCungCapEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import com.viettridao.vaccination.repository.BienDongKhoRepository;
import com.viettridao.vaccination.repository.ChiTietHdNccRepository;
import com.viettridao.vaccination.repository.LoVacXinRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.repository.VacXinRepository;
import com.viettridao.vaccination.repository.WarehouseRepository;
import com.viettridao.vaccination.service.WarehouseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final VacXinRepository vacXinRepository;
    private final LoVacXinRepository loVacXinRepository;
    private final BienDongKhoRepository bienDongKhoRepository;
    private final WarehouseMapper warehouseMapper;
    private final ChiTietHdNccRepository chiTietHDNCCRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    @Transactional
    public ImportResponse importVaccine(ImportRequest request) {
        // 1. Lấy chi tiết hóa đơn NCC theo mã lô, số hóa đơn, tên vắc xin
        ChiTietHDNCCEntity chiTietHDNCC = chiTietHDNCCRepository
                .findBySoLoAndHoaDonNCC_SoHoaDonAndVacXin_TenAndIsDeletedFalse(
                        request.getMaLoCode(),
                        request.getSoHoaDon(),
                        request.getTenVacXin()
                )
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chi tiết hóa đơn NCC!"));

        // 2. Lấy mã hóa đơn NCC
        HoaDonNCCEntity hoaDonNCC = chiTietHDNCC.getHoaDonNCC();

        // 3. Lấy lô vắc xin theo mã lô
        LoVacXinEntity loVacXin = loVacXinRepository.findByMaLoCodeIgnoreCaseAndIsDeletedFalse(chiTietHDNCC.getSoLo())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lô vắc xin!"));

        // 4. Cập nhật các trường bổ sung từ request vào lô vắc xin
        loVacXin.setNgaySanXuat(request.getNgaySanXuat());
        loVacXin.setDonVi(request.getDonVi());
        loVacXin.setHanSuDung(request.getHanSuDung());
        loVacXin.setSoGiayPhep(request.getSoGiayPhep());
        loVacXin.setDieuKienBaoQuan(request.getDieuKienBaoQuan());
        loVacXin.setNuocSanXuat(request.getNuocSanXuat());
        loVacXin.setHamLuong(request.getHamLuong());
        loVacXin.setDonGia(request.getDonGia());
        loVacXin.setNgayNhap(request.getNgayNhap());


        NhaCungCapEntity nhaCungCap = hoaDonNCC.getNhaCungCap();
        loVacXin.setNhaCungCap(nhaCungCap);

        loVacXinRepository.save(loVacXin);

        // 5. Cập nhật các trường bổ sung từ request vào vắc xin
        VacXinEntity vacXin = loVacXin.getVacXin(); // hoặc lấy từ repository nếu chưa có
        vacXin.setDoiTuongTiem(request.getDoiTuongTiem());
        vacXin.setLoai(request.getLoaiVacXin());                 // Thêm nếu có trường này
        vacXinRepository.save(vacXin); // Lưu lại nếu thay đổi

        // 6. Cập nhật trạng thái chi tiết hóa đơn NCC thành ĐÃ_NHẬP và liên kết lô vắc xin
        chiTietHDNCC.setTinhTrangNhapKho(ChiTietHDNCCEntity.TinhTrangNhapKho.DA_NHAP);
        chiTietHDNCC.setLoVacXin(loVacXin);
        chiTietHDNCCRepository.save(chiTietHDNCC);

        // 7. Tạo mới biến động kho với loại "Nhập"
        BienDongKhoEntity bienDongKho = BienDongKhoEntity.builder()
                .loVacXin(loVacXin)
                .loaiBD(BienDongKhoEntity.LoaiBienDong.NHAP)
                .loaiHoaDon(BienDongKhoEntity.LoaiHoaDon.NCC)
                .maHoaDon(hoaDonNCC.getSoHoaDon())
                .soLuong(chiTietHDNCC.getSoLuong())
                .ngayThucHien(LocalDateTime.now())
                .ghiChu("Nhập kho từ hóa đơn NCC")
                .isDeleted(Boolean.FALSE)
                .build();
        bienDongKhoRepository.save(bienDongKho);

        // 8. Trả về ImportResponse
        ImportResponse response = warehouseMapper.toImportResponse(loVacXin);
        response.setSoHoaDon(hoaDonNCC.getSoHoaDon());
        response.setTenVacXin(loVacXin.getVacXin().getTen());
        response.setMaLoCode(loVacXin.getMaLoCode());
        response.setSoLuong(loVacXin.getSoLuong());
        response.setDonGia(loVacXin.getDonGia());
        response.setNgayNhap(loVacXin.getNgayNhap());

        return response;
    }

    @Override
    public List<HoaDonChuaNhapResponse> getHoaDonChuaNhap() {
        // Truy vấn từ repo, map sang DTO
        // Ví dụ:
        List<ChiTietHDNCCEntity> chiTietList = chiTietHDNCCRepository.findChuaNhap();
        return chiTietList.stream().map(ct -> {
            HoaDonChuaNhapResponse dto = new HoaDonChuaNhapResponse();
            dto.setSoHoaDon(ct.getHoaDonNCC().getSoHoaDon());
            dto.setMaLoCode(ct.getSoLo());
            dto.setTenVacXin(ct.getVacXin().getTen());
            dto.setLoaiVacXin(ct.getVacXin().getLoai());
            dto.setNgayNhap(ct.getHoaDonNCC().getNgayHD().toString()); // format nếu LocalDate
            dto.setSoLuong(ct.getSoLuong());
            dto.setDonGia(ct.getDonGia());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WarehouseResponse exportVaccine(ExportRequest request) {
        LoVacXinEntity loVacXin = warehouseRepository.findByMaLoCodeDaNhapKho(request.getMaLoCode())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lô vắc-xin"));

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải > 0");
        }
        if (request.getQuantity() > loVacXin.getSoLuong()) {
            throw new IllegalArgumentException("Số lượng xuất vượt quá số lượng tồn kho");
        }

        int remaining = loVacXin.getSoLuong() - request.getQuantity();
        loVacXin.setSoLuong(remaining);

        // Cập nhật tình trạng vắc xin
        if (remaining == 0) {
            loVacXin.setTinhTrang(LoVacXinEntity.TinhTrang.HET);
        } else {
            loVacXin.setTinhTrang(LoVacXinEntity.TinhTrang.CO);
        }

        loVacXinRepository.save(loVacXin);

        // --- Cập nhật biến động kho ---
        ChiTietHDNCCEntity chiTietHDNCC = chiTietHDNCCRepository.findFirstByLoVacXinAndIsDeletedFalse(loVacXin).orElse(null);

        String maHoaDon = null;
        if (chiTietHDNCC != null && chiTietHDNCC.getHoaDonNCC() != null) {
            maHoaDon = chiTietHDNCC.getHoaDonNCC().getId();
        } else {
            maHoaDon = "Ăn Hối Lộ"; 
        }

        BienDongKhoEntity bienDong = new BienDongKhoEntity();
        bienDong.setIsDeleted(Boolean.FALSE);
        bienDong.setLoVacXin(loVacXin);
        bienDong.setLoaiBD(BienDongKhoEntity.LoaiBienDong.XUAT);
        bienDong.setSoLuong(request.getQuantity());
        bienDong.setMaHoaDon(maHoaDon); // String
        bienDong.setLoaiHoaDon(BienDongKhoEntity.LoaiHoaDon.KHAC); // Loại hóa đơn là NCC
        bienDong.setGhiChu("Xuất kho vắc-xin từ lô " + loVacXin.getMaLoCode());
        bienDong.setNgayThucHien(java.time.LocalDateTime.now());

        // --- Lấy tài khoản thực hiện từ session ---
        TaiKhoanEntity taiKhoan = null;
        Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof com.viettridao.vaccination.model.TaiKhoanEntity) {
            taiKhoan = (TaiKhoanEntity) principal;
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            taiKhoan = taiKhoanRepository.findByTenDangNhapAndIsDeletedFalse(username).orElse(null);
        }
        bienDong.setThucHienBoi(taiKhoan);

        bienDongKhoRepository.save(bienDong);

        return warehouseMapper.toWarehouseResponse(loVacXin);
    }

    @Override
    public Page<WarehouseResponse> getWarehouses(String searchType, String keyword, int pageNo, int pageSize) {
        if (pageNo < 0) pageNo = 0;
        Page<WarehouseResponse> warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);

        if (pageNo >= warehousePage.getTotalPages() && warehousePage.getTotalPages() > 0) {
            pageNo = warehousePage.getTotalPages() - 1;
            warehousePage = fetchPage(searchType, keyword, pageNo, pageSize);
        }

        List<WarehouseResponse> content = warehousePage.getContent().stream()
                .map(item -> {
                    if ("CO".equalsIgnoreCase(item.getTinhTrangVacXin())) {
                        item.setTinhTrangVacXin("Có");
                    } else if ("HET".equalsIgnoreCase(item.getTinhTrangVacXin())) {
                        item.setTinhTrangVacXin("Hết");
                    }
                    return item;
                })
                .toList();

        return new PageImpl<>(content, warehousePage.getPageable(), warehousePage.getTotalElements());
    }

    private Page<WarehouseResponse> fetchPage(String searchType, String keyword, int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize);

        // Truy vấn lô vắc xin liên kết với Chi tiết hóa đơn NCC có trạng thái ĐÃ_NHẬP
        Page<LoVacXinEntity> entities;

        if (keyword == null || keyword.trim().isEmpty()) {
            entities = warehouseRepository.findAllDaNhapKho(pageable);
        } else {
            switch (searchType) {
                case "tenVacXin":
                    entities = warehouseRepository.findByTenVacXinDaNhapKho(keyword.trim(), pageable);
                    break;
                case "loaiVacXin":
                    entities = warehouseRepository.findByLoaiVacXinDaNhapKho(keyword.trim(), pageable);
                    break;
                case "nuocSanXuat":
                    entities = warehouseRepository.findByNuocSanXuatDaNhapKho(keyword.trim(), pageable);
                    break;
                case "doiTuongTiem":
                    entities = warehouseRepository.findByDoiTuongTiemDaNhapKho(keyword.trim(), pageable);
                    break;
                default:
                    entities = warehouseRepository.findAllDaNhapKho(pageable);
                    break;
            }
        }

        // Map entity sang response
        return entities.map(warehouseMapper::toWarehouseResponse);
    }
}