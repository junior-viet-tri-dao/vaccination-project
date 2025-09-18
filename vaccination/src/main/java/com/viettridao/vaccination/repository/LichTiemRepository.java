package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.LichTiemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LichTiemRepository extends JpaRepository<LichTiemEntity, String> {
    @Query("SELECT l FROM LichTiemEntity l WHERE l.vacXin.id = :vacXinId AND l.ngayGio = :ngayGio AND (l.isDeleted = false OR l.isDeleted IS NULL)")
    Optional<LichTiemEntity> findByVacXinIdAndNgayGio(@Param("vacXinId") String vacXinId, @Param("ngayGio") LocalDateTime ngayGio);
}