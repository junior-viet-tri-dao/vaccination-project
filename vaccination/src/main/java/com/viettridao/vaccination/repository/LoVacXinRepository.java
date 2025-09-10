package com.viettridao.vaccination.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.LoVacXinEntity;

@Repository
public interface LoVacXinRepository extends JpaRepository<LoVacXinEntity, UUID> {
    // JpaRepository đã hỗ trợ sẵn phân trang bằng Page<T>
}

