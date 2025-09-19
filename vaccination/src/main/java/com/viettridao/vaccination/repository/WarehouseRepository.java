package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.LoVacXinEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<LoVacXinEntity, String> {

	// Phân trang tất cả lô vắc xin liên kết với chi tiết hóa đơn NCC đã nhập kho
	// (tình trạng ĐÃ_NHẬP)
	@Query("SELECT l FROM LoVacXinEntity l JOIN ChiTietHDNCCEntity c ON c.loVacXin = l "
			+ "WHERE l.isDeleted = false AND c.tinhTrangNhapKho = 'DA_NHAP'")
	Page<LoVacXinEntity> findAllDaNhapKho(Pageable pageable);

	// Tìm kiếm theo tên vắc xin (ignore case, đã nhập kho, phân trang, chưa bị xóa
	// mềm)
	@Query("SELECT l FROM LoVacXinEntity l JOIN ChiTietHDNCCEntity c ON c.loVacXin = l "
			+ "WHERE l.isDeleted = false AND c.tinhTrangNhapKho = 'DA_NHAP' "
			+ "AND LOWER(l.vacXin.ten) LIKE LOWER(CONCAT('%', :tenVacXin, '%'))")
	Page<LoVacXinEntity> findByTenVacXinDaNhapKho(@Param("tenVacXin") String tenVacXin, Pageable pageable);

	// Tìm kiếm theo loại vắc xin (ignore case, đã nhập kho, phân trang, chưa bị xóa
	// mềm)
	@Query("SELECT l FROM LoVacXinEntity l JOIN ChiTietHDNCCEntity c ON c.loVacXin = l "
			+ "WHERE l.isDeleted = false AND c.tinhTrangNhapKho = 'DA_NHAP' "
			+ "AND LOWER(l.vacXin.loai) LIKE LOWER(CONCAT('%', :loaiVacXin, '%'))")
	Page<LoVacXinEntity> findByLoaiVacXinDaNhapKho(@Param("loaiVacXin") String loaiVacXin, Pageable pageable);

	// Tìm kiếm theo nước sản xuất (ignore case, đã nhập kho, phân trang, chưa bị
	// xóa mềm)
	@Query("SELECT l FROM LoVacXinEntity l JOIN ChiTietHDNCCEntity c ON c.loVacXin = l "
			+ "WHERE l.isDeleted = false AND c.tinhTrangNhapKho = 'DA_NHAP' "
			+ "AND LOWER(l.nuocSanXuat) LIKE LOWER(CONCAT('%', :nuocSanXuat, '%'))")
	Page<LoVacXinEntity> findByNuocSanXuatDaNhapKho(@Param("nuocSanXuat") String nuocSanXuat, Pageable pageable);

	// Tìm kiếm theo đối tượng tiêm chủng (ignore case, đã nhập kho, phân trang,
	// chưa bị xóa mềm)
	@Query("SELECT l FROM LoVacXinEntity l JOIN ChiTietHDNCCEntity c ON c.loVacXin = l "
			+ "WHERE l.isDeleted = false AND c.tinhTrangNhapKho = 'DA_NHAP' "
			+ "AND LOWER(l.vacXin.doiTuongTiem) LIKE LOWER(CONCAT('%', :doiTuongTiem, '%'))")
	Page<LoVacXinEntity> findByDoiTuongTiemDaNhapKho(@Param("doiTuongTiem") String doiTuongTiem, Pageable pageable);

	// Tìm theo mã lô đã nhập kho, chưa bị xóa mềm
	@Query("SELECT l FROM LoVacXinEntity l JOIN ChiTietHDNCCEntity c ON c.loVacXin = l "
			+ "WHERE l.isDeleted = false AND c.tinhTrangNhapKho = 'DA_NHAP' AND LOWER(l.maLoCode) = LOWER(:maLoCode)")
	Optional<LoVacXinEntity> findByMaLoCodeDaNhapKho(@Param("maLoCode") String maLoCode);

	Optional<LoVacXinEntity> findByMaLoCodeIgnoreCaseAndIsDeletedFalse(String maLoCode);

}