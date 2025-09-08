package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, String> {
}
