package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.QuyenHanEntity;
import com.viettridao.vaccination.model.VaiTroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuyenHanRepository extends JpaRepository<QuyenHanEntity, String> {
    Optional<QuyenHanEntity> findByTen(String ten);
}
