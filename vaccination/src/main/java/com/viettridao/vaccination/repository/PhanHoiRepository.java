package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import com.viettridao.vaccination.model.BaoCaoPhanUngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.PhanHoiEntity;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoiEntity, String> {

    List<PhanHoiEntity> findByLoaiPhanHoiAndIsDeletedFalse(PhanHoiEntity.LoaiPhanHoi loai);

    Optional<PhanHoiEntity> findByIdAndIsDeletedFalse(String id);

}
