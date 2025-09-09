package com.viettridao.vaccination.mapper;

import com.viettridao.vaccination.dto.request.finance.CreateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.request.finance.UpdateTransactionCustomerRequest;
import com.viettridao.vaccination.dto.response.finance.TransactionCustomerResponse;
import com.viettridao.vaccination.model.InvoiceEntity;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionCustomerMapper {
	@Mapping(target = "invoiceId", source = "invoiceId")
	@Mapping(target = "invoiceCode", source = "invoiceCode")
	@Mapping(target = "quantity", source = "quantityInvoice")
	@Mapping(target = "price", source = "customerPrice")
	@Mapping(target = "customerName", ignore = true) // sẽ set thủ công trong Service
	@Mapping(target = "vaccineCode", ignore = true) // sẽ set thủ công trong Service
	@Mapping(target = "dateTime", ignore = true) // sẽ set thủ công trong Service
	@Mapping(target = "index", ignore = true)
	TransactionCustomerResponse toDto(InvoiceEntity entity);

	List<TransactionCustomerResponse> toDtoList(List<InvoiceEntity> entities);

	@Mapping(target = "invoiceId", ignore = true)
	@Mapping(target = "quantityInvoice", source = "quantity")
	@Mapping(target = "customerPrice", source = "price")
	@Mapping(target = "supplierPrice", ignore = true)
	@Mapping(target = "totalAmount", ignore = true)
	@Mapping(target = "isDeleted", constant = "false")
	@Mapping(target = "vaccine", ignore = true)
	@Mapping(target = "supplier", ignore = true)
	@Mapping(target = "medicalRecordEntity", ignore = true)
	InvoiceEntity fromCreateRequest(CreateTransactionCustomerRequest request);

	@Mapping(target = "invoiceId", source = "invoiceId")
	@Mapping(target = "quantityInvoice", source = "quantity")
	@Mapping(target = "customerPrice", source = "price")
	@Mapping(target = "supplierPrice", ignore = true)
	@Mapping(target = "totalAmount", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "vaccine", ignore = true)
	@Mapping(target = "supplier", ignore = true)
	@Mapping(target = "medicalRecordEntity", ignore = true)
	void updateEntityFromRequest(UpdateTransactionCustomerRequest request, @MappingTarget InvoiceEntity entity);
}
