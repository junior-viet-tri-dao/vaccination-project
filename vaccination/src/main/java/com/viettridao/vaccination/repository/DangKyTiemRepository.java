package com.viettridao.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.DangKyTiemEntity;

@Repository
public interface DangKyTiemRepository extends JpaRepository<DangKyTiemEntity, String> {
    List<DangKyTiemEntity> findByBenhNhanIdAndIsDeletedFalse(String benhNhanId);
}

