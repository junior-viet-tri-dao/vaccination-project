package com.viettridao.vaccination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.InvoiceEntity;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {

    // Lấy tất cả invoice chưa xóa mềm, có phân trang
    Page<InvoiceEntity> findByIsDeletedFalse(Pageable pageable);
    
    @Query("SELECT i FROM InvoiceEntity i " +
            "LEFT JOIN InvoiceExtraEntity e ON i = e.invoice " +
            "WHERE i.isDeleted = false " +
            "ORDER BY e.dateTime DESC")
     Page<InvoiceEntity> findAllSortedByDateTime(Pageable pageable);
}

