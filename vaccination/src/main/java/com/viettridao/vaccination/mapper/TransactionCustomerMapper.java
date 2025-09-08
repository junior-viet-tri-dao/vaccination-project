package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.model.InvoiceEntity;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionCustomerMapper {

    // Entity -> DTO
    @Mapping(target = "invoiceId", source = "invoiceId")
    @Mapping(target = "invoiceCode", source = "invoiceId")
    @Mapping(target = "vaccineCode", source = "vaccine.vaccineCode")
    @Mapping(target = "quantity", source = "quantityInvoice")
    @Mapping(target = "price", source = "customerPrice")
    @Mapping(target = "customerName", source = "medicalRecordEntity.registrationDetail.patient.patientName")
    @Mapping(target = "dateTime", source = "medicalRecordEntity.vaccinationTime")
    @Mapping(target = "index", ignore = true) // STT tính trong service/controller
    TransactionCustomerResponse toDto(InvoiceEntity entity);

    List<TransactionCustomerResponse> toDtoList(List<InvoiceEntity> entities);

    // CreateRequest -> Entity
    @Mapping(target = "invoiceId", ignore = true) // ID sinh tự động
    @Mapping(target = "quantityInvoice", source = "quantity")
    @Mapping(target = "customerPrice", source = "price")
    @Mapping(target = "supplierPrice", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "vaccine", ignore = true) // sẽ set thủ công trong service
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "medicalRecordEntity", ignore = true)
    InvoiceEntity fromCreateRequest(CreateTransactionCustomerRequest request);

    // UpdateRequest -> Entity
    @Mapping(target = "invoiceId", source = "invoiceId")
    @Mapping(target = "quantityInvoice", source = "quantity")
    @Mapping(target = "customerPrice", source = "price")
    @Mapping(target = "supplierPrice", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "vaccine", ignore = true) // set thủ công
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "medicalRecordEntity", ignore = true)
    void updateEntityFromRequest(UpdateTransactionCustomerRequest request,
                                 @MappingTarget InvoiceEntity entity);
}
