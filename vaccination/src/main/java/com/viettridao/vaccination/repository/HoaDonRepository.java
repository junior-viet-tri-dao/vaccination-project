package com.viettridao.vaccination.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.HoaDonEntity;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDonEntity, String> {

    List<HoaDonEntity> findAllByIsDeletedFalse();

    Optional<HoaDonEntity> findBySoHoaDonAndIsDeletedFalse(String soHoaDon);
    
    @Query("SELECT hd FROM HoaDonEntity hd LEFT JOIN FETCH hd.chiTietHDs WHERE hd.isDeleted = false")
    List<HoaDonEntity> findAllWithChiTietHDs();
      
}
