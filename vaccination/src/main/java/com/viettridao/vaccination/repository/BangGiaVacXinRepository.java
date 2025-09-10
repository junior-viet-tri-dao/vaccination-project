package com.viettridao.vaccination.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.BangGiaVacXinEntity;

@Repository
public interface BangGiaVacXinRepository extends JpaRepository<BangGiaVacXinEntity, UUID> {

    // Tìm tất cả bảng giá hiện tại theo vaccineId có phân trang
    @Query("""
        SELECT b 
        FROM BangGiaVacXinEntity b
        WHERE b.vacXin.id = :vacXinId
          AND (b.hieuLucTu IS NULL OR b.hieuLucTu <= :today)
          AND (b.hieuLucDen IS NULL OR b.hieuLucDen >= :today)
        ORDER BY b.hieuLucTu DESC
    """)
    Page<BangGiaVacXinEntity> findGiaHienTai(@Param("vacXinId") UUID vacXinId,
                                             @Param("today") LocalDate today,
                                             Pageable pageable);
}
