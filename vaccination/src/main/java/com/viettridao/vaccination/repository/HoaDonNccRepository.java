package com.viettridao.vaccination.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.HoaDonNCCEntity;

@Repository
public interface HoaDonNccRepository extends JpaRepository<HoaDonNCCEntity, String> {
    Optional<HoaDonNCCEntity> findBySoHoaDon(String soHoaDon);
}