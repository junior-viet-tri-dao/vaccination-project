package com.viettridao.vaccination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.VaccineBatchEntity;

@Repository
public interface VaccineBatchRepository extends JpaRepository<VaccineBatchEntity, String> {
}

