package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {

    // Tìm NCC theo tên (dùng khi tạo giao dịch)
    Optional<SupplierEntity> findBySupplierNameAndIsDeletedFalse(String supplierName);

    // Check tồn tại theo tên
    boolean existsBySupplierNameAndIsDeletedFalse(String supplierName);
}