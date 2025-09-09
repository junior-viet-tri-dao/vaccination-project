package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionSupplierRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionSupplierRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionSupplierResponse;
import com.viettridao.vaccination.model.InvoiceEntity;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionSupplierMapper {

    // Entity -> DTO
    @Mapping(target = "invoiceId", source = "invoiceId")
    @Mapping(target = "invoiceCode", source = "invoiceCode")
    @Mapping(target = "quantity", source = "quantityInvoice")
    @Mapping(target = "price", source = "supplierPrice")
    @Mapping(target = "supplierName", ignore = true) // set thủ công trong Service
    @Mapping(target = "vaccineCode", ignore = true) // set thủ công trong Service
    @Mapping(target = "dateTimeSupplier", source = "dateTimeSupplier")
    TransactionSupplierResponse toDto(InvoiceEntity entity);

    List<TransactionSupplierResponse> toDtoList(List<InvoiceEntity> entities);

    // CreateRequest -> Entity
    @Mapping(target = "invoiceId", ignore = true)
    @Mapping(target = "quantityInvoice", source = "quantity")
    @Mapping(target = "supplierPrice", source = "price")
    @Mapping(target = "customerPrice", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "vaccine", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "medicalRecordEntity", ignore = true)
    InvoiceEntity fromCreateRequest(CreateTransactionSupplierRequest request);

    // UpdateRequest -> Entity (dùng void update cho chuẩn)
    @Mapping(target = "invoiceId", source = "invoiceId")
    @Mapping(target = "quantityInvoice", source = "quantity")
    @Mapping(target = "supplierPrice", source = "price")
    @Mapping(target = "customerPrice", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "vaccine", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "medicalRecordEntity", ignore = true)
    void updateEntityFromRequest(UpdateTransactionSupplierRequest request, @MappingTarget InvoiceEntity entity);
}