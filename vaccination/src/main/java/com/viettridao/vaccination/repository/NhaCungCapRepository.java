package com.viettridao.vaccination.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.NhaCungCapEntity;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCapEntity, String> {
	Optional<NhaCungCapEntity> findByTen(String ten);
}
