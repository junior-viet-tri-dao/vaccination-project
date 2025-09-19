package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.VacXinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LichTiemRepository extends JpaRepository<LichTiemEntity, String> {

    // Tìm lịch tiêm theo id vắc xin + thời gian chỉ định, lọc bản ghi chưa xóa
    @Query("SELECT l FROM LichTiemEntity l WHERE l.vacXin.id = :vacXinId AND l.ngayGio = :ngayGio AND (l.isDeleted = false OR l.isDeleted IS NULL)")
    Optional<LichTiemEntity> findByVacXinIdAndNgayGio(@Param("vacXinId") String vacXinId, @Param("ngayGio") LocalDateTime ngayGio);

    // Lấy lịch tiêm sớm nhất của một loại vắc xin
    LichTiemEntity findTopByVacXinOrderByNgayGioAsc(VacXinEntity vacXin);
}