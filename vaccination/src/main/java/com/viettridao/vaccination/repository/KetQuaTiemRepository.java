package com.viettridao.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.KetQuaTiemEntity;

@Repository
public interface KetQuaTiemRepository extends JpaRepository<KetQuaTiemEntity, String> {
	List<KetQuaTiemEntity> findAllByBenhNhan_IdAndIsDeletedFalse(String maBenhNhan);

	List<KetQuaTiemEntity> findAllByIsDeletedFalse();

	List<KetQuaTiemEntity> findAllByBenhNhanIdAndIsDeletedFalse(String maBenhNhan);
	
    List<KetQuaTiemEntity> findByBenhNhanIdAndIsDeletedFalse(String benhNhanId);

}