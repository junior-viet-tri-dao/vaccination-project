package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.PhanHoiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhanHoiCapCaoRepository extends JpaRepository<PhanHoiEntity, String> {

    /**
     * Lấy danh sách phản hồi chưa bị xóa mềm, sắp xếp theo ngày tạo giảm dần.
     */
    Page<PhanHoiEntity> findByIsDeletedFalseOrderByNgayTaoDesc(Pageable pageable);

    /**
     * Lấy danh sách phản hồi theo mã bệnh nhân, chưa bị xóa mềm, sắp xếp theo ngày tạo giảm dần.
     */
    Page<PhanHoiEntity> findByBenhNhan_IdAndIsDeletedFalseOrderByNgayTaoDesc(String benhNhanId, Pageable pageable);

}