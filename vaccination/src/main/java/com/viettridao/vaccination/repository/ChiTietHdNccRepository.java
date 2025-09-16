package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.ChiTietHDNCCEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;

@Repository
public interface ChiTietHdNccRepository extends JpaRepository<ChiTietHDNCCEntity, String> {
	Optional<ChiTietHDNCCEntity> findFirstByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);

	List<ChiTietHDNCCEntity> findAllByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);

	void deleteByLoVacXin(LoVacXinEntity loVacXin);

	List<ChiTietHDNCCEntity> findByHoaDonNCCSoHoaDon(String soHoaDon);

	Optional<ChiTietHDNCCEntity> findByIdAndTinhTrangNhapKhoAndIsDeletedFalse(String id,
			ChiTietHDNCCEntity.TinhTrangNhapKho tinhTrangNhapKho);

	Optional<ChiTietHDNCCEntity> findBySoLoAndHoaDonNCC_SoHoaDonAndVacXin_TenAndIsDeletedFalse(String soLo,
			String soHoaDon, String tenVacXin);

	@Query("SELECT DISTINCT c.hoaDonNCC.soHoaDon FROM ChiTietHDNCCEntity c WHERE c.tinhTrangNhapKho = 'CHUA_NHAP' AND c.isDeleted = false")
	List<String> findSoHoaDonChuaNhap();

	// Trả về các chi tiết hóa đơn NCC chưa nhập kho
	@Query("SELECT c FROM ChiTietHDNCCEntity c WHERE c.tinhTrangNhapKho = 'CHUA_NHAP' AND c.isDeleted = false")
	List<ChiTietHDNCCEntity> findChuaNhap();

	// Nếu muốn chỉ lấy chưa xóa
	List<ChiTietHDNCCEntity> findByHoaDonNCCSoHoaDonAndIsDeletedFalse(String soHoaDon);

	boolean existsByLoVacXin_MaLoCode(String maLo);
	boolean existsByHoaDonNCC_SoHoaDon(String soHoaDon);

}