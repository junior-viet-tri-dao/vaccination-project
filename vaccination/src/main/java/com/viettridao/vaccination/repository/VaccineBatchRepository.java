package com.viettridao.vaccination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.VaccineBatchEntity;

@Repository
public interface VaccineBatchRepository extends JpaRepository<VaccineBatchEntity, String> {
    @Query("SELECT v FROM VaccineBatchEntity v WHERE v.isDeleted = false")
    Page<VaccineBatchEntity> findAllActive(Pageable pageable);
}