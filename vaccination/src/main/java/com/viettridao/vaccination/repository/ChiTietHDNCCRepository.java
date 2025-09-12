package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.ChiTietHDNCCEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiTietHDNCCRepository extends JpaRepository<ChiTietHDNCCEntity, String> {
    Optional<ChiTietHDNCCEntity> findFirstByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);

}