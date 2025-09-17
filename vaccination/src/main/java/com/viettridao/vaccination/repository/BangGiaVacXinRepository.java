package com.viettridao.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.BangGiaVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface BangGiaVacXinRepository extends JpaRepository<BangGiaVacXinEntity, String> {

	// Lấy giá hiện tại của 1 vắc xin
	List<BangGiaVacXinEntity> findByVacXinIdOrderByHieuLucTuDesc(String maCode);

	void deleteByVacXin(VacXinEntity vacXin);

	List<BangGiaVacXinEntity> findByVacXinMaCodeOrderByHieuLucTuDesc(String maCode);

	BangGiaVacXinEntity findTopByVacXin_IdOrderByHieuLucTuDesc(String maVacXin);
	
    BangGiaVacXinEntity findTopByVacXinOrderByHieuLucTuDesc(VacXinEntity vacXin);


}
