package com.viettridao.vaccination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.LichTiemEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface LichTiemRepository extends JpaRepository<LichTiemEntity, String> {
	LichTiemEntity findTopByVacXinOrderByNgayGioAsc(VacXinEntity vacXin);
}