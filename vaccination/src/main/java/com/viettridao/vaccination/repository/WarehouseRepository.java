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

    // Phân trang tất cả lô vắc xin chưa bị xóa mềm
    @Query("SELECT l FROM LoVacXinEntity l WHERE l.isDeleted = false")
    Page<LoVacXinEntity> findAllNotDeleted(Pageable pageable);

    // Tìm kiếm theo tên vắc xin (ignore case, phân trang, chưa bị xóa mềm)
    @Query("SELECT l FROM LoVacXinEntity l WHERE l.isDeleted = false AND LOWER(l.vacXin.ten) LIKE LOWER(CONCAT('%', :tenVacXin, '%'))")
    Page<LoVacXinEntity> findByTenVacXinNotDeleted(@Param("tenVacXin") String tenVacXin, Pageable pageable);

    // Tìm kiếm theo loại vắc xin (ignore case, phân trang, chưa bị xóa mềm)
    @Query("SELECT l FROM LoVacXinEntity l WHERE l.isDeleted = false AND LOWER(l.vacXin.loai) LIKE LOWER(CONCAT('%', :loaiVacXin, '%'))")
    Page<LoVacXinEntity> findByLoaiVacXinNotDeleted(@Param("loaiVacXin") String loaiVacXin, Pageable pageable);

    // Tìm kiếm theo nước sản xuất (ignore case, phân trang, chưa bị xóa mềm)
    Page<LoVacXinEntity> findByNuocSanXuatContainingIgnoreCaseAndIsDeletedFalse(String nuocSanXuat, Pageable pageable);

    // Tìm kiếm theo độ tuổi tiêm chủng (ignore case, phân trang, chưa bị xóa mềm)
    @Query("SELECT l FROM LoVacXinEntity l WHERE l.isDeleted = false AND LOWER(l.vacXin.doiTuongTiem) LIKE LOWER(CONCAT('%', :doiTuongTiem, '%'))")
    Page<LoVacXinEntity> findByDoiTuongTiemNotDeleted(@Param("doiTuongTiem") String doiTuongTiem, Pageable pageable);

    // Tìm theo mã lô chưa bị xóa mềm
    Optional<LoVacXinEntity> findByMaLoCodeIgnoreCaseAndIsDeletedFalse(String maLoCode);
}