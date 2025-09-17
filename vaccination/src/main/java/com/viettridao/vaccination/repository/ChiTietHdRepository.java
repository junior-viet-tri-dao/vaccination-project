package com.viettridao.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.ChiTietHDEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;

@Repository
public interface ChiTietHdRepository extends JpaRepository<ChiTietHDEntity, String> {

	// Nếu muốn, bạn có thể thêm phương thức tìm tất cả theo LoVacXin
	List<ChiTietHDEntity> findByLoVacXin(LoVacXinEntity loVacXin);

	List<ChiTietHDEntity> findAllByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);
	
    List<ChiTietHDEntity> findAllByIsDeletedFalseAndHoaDonIsDeletedFalse();

}
