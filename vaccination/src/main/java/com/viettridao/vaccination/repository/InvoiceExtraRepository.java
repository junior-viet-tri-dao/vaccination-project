package com.viettridao.vaccination.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.InvoiceExtraEntity;

@Repository
public interface InvoiceExtraRepository extends JpaRepository<InvoiceExtraEntity, Long> {
    Optional<InvoiceExtraEntity> findByInvoice_InvoiceId(String invoiceId);
}
