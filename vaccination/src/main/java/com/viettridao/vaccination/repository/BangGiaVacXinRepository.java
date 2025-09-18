package com.viettridao.vaccination.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface BangGiaVacXinRepository extends JpaRepository<BangGiaVacXinEntity, String> {

	// Lấy giá hiện tại của 1 vắc xin
    List<BangGiaVacXinEntity> findByVacXinIdOrderByHieuLucTuDesc(String maCode);

    // Lấy giá còn hiệu lực tại ngày chỉ định (ưu tiên giá mới nhất)
    @Query("""
        SELECT bg FROM BangGiaVacXinEntity bg
        WHERE bg.vacXin.id = :vacXinId
          AND (bg.hieuLucTu IS NULL OR bg.hieuLucTu <= :targetDate)
          AND (bg.hieuLucDen IS NULL OR bg.hieuLucDen >= :targetDate)
          AND (bg.isDeleted = false OR bg.isDeleted IS NULL)
        ORDER BY bg.hieuLucTu DESC
        """)
    Optional<BangGiaVacXinEntity> findCurrentPriceByVacXinIdAndDate(
            @Param("vacXinId") String vacXinId,
            @Param("targetDate") LocalDate targetDate
    );
    
    void deleteByVacXin(VacXinEntity vacXin);

    Page<BangGiaVacXinEntity> findAllByIsDeletedFalse(Pageable pageable);

    Page<BangGiaVacXinEntity> findByVacXin_MaCodeContainingIgnoreCaseAndIsDeletedFalse(String maCode, Pageable pageable);

    Page<BangGiaVacXinEntity> findByVacXin_TenContainingIgnoreCaseAndIsDeletedFalse(String tenVacXin, Pageable pageable);

    Page<BangGiaVacXinEntity> findByVacXin_MoTaContainingIgnoreCaseAndIsDeletedFalse(String moTa, Pageable pageable);
}
