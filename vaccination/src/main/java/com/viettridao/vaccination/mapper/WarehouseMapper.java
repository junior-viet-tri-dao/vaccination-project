package com.viettridao.vaccination.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.viettridao.vaccination.dto.request.warehouse.ImportRequest;
import com.viettridao.vaccination.dto.response.warehouse.ImportResponse;
import com.viettridao.vaccination.dto.response.warehouse.WarehouseResponse;
import com.viettridao.vaccination.model.VaccineBatchEntity;
import com.viettridao.vaccination.model.VaccineEntity;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    // Mapper cho WarehouseResponse
    @Mapping(target = "batchCode", source = "batchCode")
    @Mapping(target = "vaccineName", source = "vaccine.vaccineName")
    @Mapping(target = "vaccineTypeName", source = "vaccine.vaccineType.vaccineTypeName")
    @Mapping(target = "receivedDate", source = "receivedDate")
    @Mapping(target = "licenseNumber", source = "licenseNumber")
    @Mapping(target = "countryOfOrigin", source = "countryOfOrigin")
    @Mapping(target = "dosage", source = "vaccine.dosage")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "expirationDate", source = "vaccine.expirationDate")
    @Mapping(target = "storageCondition", source = "vaccine.storageCondition")
    @Mapping(target = "ageGroup", source = "vaccine.ageGroup")
    @Mapping(target = "status", source = "status")
    WarehouseResponse toResponse(VaccineBatchEntity entity);

    // Mapper cho ImportResponse
    @Mapping(target = "batchCode", source = "batchCode")
    @Mapping(target = "vaccineName", source = "vaccine.vaccineName")
    @Mapping(target = "vaccineType", source = "vaccine.vaccineType.vaccineTypeName")
    @Mapping(target = "receivedDate", source = "receivedDate")
    @Mapping(target = "licenseNumber", source = "licenseNumber")
    @Mapping(target = "originCountry", source = "countryOfOrigin")
    @Mapping(target = "price", source = "vaccine.unitPrice")
    @Mapping(target = "dosage", source = "vaccine.dosage")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "expiryDate", source = "vaccine.expirationDate")
    @Mapping(target = "storageConditions", source = "vaccine.storageCondition")
    @Mapping(target = "vaccinationAge", source = "vaccine.ageGroup")
    ImportResponse toImportResponse(VaccineBatchEntity entity);

    // Mapper từ ImportRequest → VaccineEntity + VaccineBatchEntity
    @Mapping(target = "vaccineName", source = "vaccineName")
    @Mapping(target = "dosage", source = "dosage")
    @Mapping(target = "unitPrice", source = "price")
    @Mapping(target = "expirationDate", source = "expiryDate")
    @Mapping(target = "storageCondition", source = "storageConditions")
    @Mapping(target = "ageGroup", source = "vaccinationAge")
    @Mapping(target = "vaccineType.vaccineTypeName", source = "vaccineType")
    @Mapping(target = "unit", source = "unit")
    @Mapping(target = "vaccineCode", source = "vaccineCode")
    VaccineEntity toVaccineEntity(ImportRequest request);

    @Mapping(target = "quantity", source = "request.quantity")
    @Mapping(target = "receivedDate", source = "request.receivedDate")
    @Mapping(target = "licenseNumber", source = "request.licenseNumber")
    @Mapping(target = "countryOfOrigin", source = "request.originCountry")
    @Mapping(target = "vaccine", source = "vaccineEntity") // gán VaccineEntity vào field vaccine
    @Mapping(target = "productionYear", source = "request.productionYear")
    VaccineBatchEntity toVaccineBatchEntity(ImportRequest request, VaccineEntity vaccineEntity);
}