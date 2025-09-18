package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.KetQuaTiemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KetQuaTiemRepository extends JpaRepository<KetQuaTiemEntity, String> {
    // Lấy danh sách kết quả tiêm của một bệnh nhân, chỉ lấy bản ghi chưa xóa
    List<KetQuaTiemEntity> findByBenhNhan_IdAndIsDeletedFalseOrderByNgayTiemAsc(String benhNhanId);
}