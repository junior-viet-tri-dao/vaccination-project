package com.viettridao.vaccination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viettridao.vaccination.model.VaccineEntity;

import java.util.List;
import java.util.Optional;

public interface VaccineRepository extends JpaRepository<VaccineEntity, String> {

    Optional<VaccineEntity> findByVaccineCodeAndIsDeletedFalse(String vaccineCode);

    List<VaccineEntity> findByIsDeletedFalse();
}
