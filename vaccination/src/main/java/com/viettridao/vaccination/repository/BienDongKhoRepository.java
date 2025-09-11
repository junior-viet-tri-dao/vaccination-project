package com.viettridao.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.BienDongKhoEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;

@Repository
public interface BienDongKhoRepository extends JpaRepository<BienDongKhoEntity, String> {

	List<BienDongKhoEntity> findAllByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);

	void deleteByLoVacXin(LoVacXinEntity loVacXin);
}
