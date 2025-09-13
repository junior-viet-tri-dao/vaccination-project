package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.ChiTietHDNCCEntity;
import com.viettridao.vaccination.model.LoVacXinEntity;

@Repository
public interface ChiTietHdNccRepository extends JpaRepository<ChiTietHDNCCEntity, String> {
    Optional<ChiTietHDNCCEntity> findFirstByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);
    
	List<ChiTietHDNCCEntity> findAllByLoVacXinAndIsDeletedFalse(LoVacXinEntity loVacXin);

	void deleteByLoVacXin(LoVacXinEntity loVacXin);
	    
    List<ChiTietHDNCCEntity> findByHoaDonNCCSoHoaDon(String soHoaDon);

    // Nếu muốn chỉ lấy chưa xóa
    List<ChiTietHDNCCEntity> findByHoaDonNCCSoHoaDonAndIsDeletedFalse(String soHoaDon);
    
}