package com.viettridao.vaccination.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface LichTiemRepository extends JpaRepository<LichTiemEntity, String> {

    List<LichTiemEntity> findAllByIsDeletedFalse();

    Optional<LichTiemEntity> findByVacXinTen(String tenVacXin);

    // Tìm lịch tiêm theo id vắc xin + thời gian chỉ định, lọc bản ghi chưa xóa
    @Query("SELECT l FROM LichTiemEntity l WHERE l.vacXin.id = :vacXinId AND l.ngayGio = :ngayGio AND (l.isDeleted = false OR l.isDeleted IS NULL)")
    List<LichTiemEntity> findByVacXinIdAndNgayGio(@Param("vacXinId") String vacXinId,
                                                  @Param("ngayGio") LocalDateTime ngayGio);

    // Lấy lịch tiêm sớm nhất của một loại vắc xin
    LichTiemEntity findTopByVacXinOrderByNgayGioAsc(VacXinEntity vacXin);

    List<LichTiemEntity> findByIsDeletedFalse();

    @Query("SELECT DISTINCT l.vacXin.ten FROM LichTiemEntity l WHERE l.isDeleted = false")
    List<String> findDistinctLoaiVacXin();


    List<LichTiemEntity> findByNgayGioBetweenAndIsDeletedFalse(LocalDateTime start, LocalDateTime end);
}