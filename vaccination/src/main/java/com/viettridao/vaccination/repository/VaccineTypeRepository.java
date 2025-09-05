package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.VaccineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineTypeEntity, String> {
    // lấy tất cả loại vắc-xin
    List<VaccineTypeEntity> findAll();
}

