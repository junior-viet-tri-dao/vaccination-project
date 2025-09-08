package com.viettridao.vaccination.dto.response.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportResponse {
	private String batchCode;

	private String vaccineName;

	private String vaccineType;

	private LocalDate receivedDate;

	private String licenseNumber;

	private String originCountry;

	private Integer price;

	private String dosage;

	private Integer quantity;

	private LocalDate expiryDate;

	private String storageConditions;

	private String vaccinationAge;

	private String vaccineCode;

	private String productionYear;

	private String unit;

}