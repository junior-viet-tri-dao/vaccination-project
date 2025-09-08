package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {

    // Lấy tất cả invoice chưa xóa mềm, có phân trang
    Page<InvoiceEntity> findByIsDeletedFalse(Pageable pageable);
}

