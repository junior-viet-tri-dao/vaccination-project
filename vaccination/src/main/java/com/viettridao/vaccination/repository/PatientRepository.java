package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {

    // Lấy tất cả bệnh nhân chưa bị xóa mềm (nếu có cờ isDeleted)
    List<PatientEntity> findByIsDeletedFalse();

    // Tìm bệnh nhân theo mã
    Optional<PatientEntity> findByPatientCode(String patientCode);
}

