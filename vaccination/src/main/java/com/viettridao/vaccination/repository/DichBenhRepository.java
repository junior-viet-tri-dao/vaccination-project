package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.DichBenhEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DichBenhRepository extends JpaRepository<DichBenhEntity, String> {

    // Phân trang, chỉ lấy những bản ghi chưa bị xóa mềm, sắp xếp giảm dần theo thời điểm khảo sát
    @Query("SELECT d FROM DichBenhEntity d WHERE d.isDeleted = false ORDER BY d.thoiDiemKhaoSat DESC")
    Page<DichBenhEntity> findActiveOrderByThoiDiemKhaoSatDesc(Pageable pageable);
}