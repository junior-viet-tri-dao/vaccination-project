package com.viettridao.vaccination.dto.request.warehouse;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportRequest {

    @NotBlank(message = "Tên vắc-xin không được để trống")
    @Size(min = 2, max = 100, message = "Tên vắc-xin từ 2 đến 100 ký tự")
    private String vaccineName;

    @NotBlank(message = "Loại vắc-xin không được để trống")
    @Size(max = 50, message = "Loại vắc-xin tối đa 50 ký tự")
    private String vaccineType;

    @NotNull(message = "Ngày nhập không được để trống")
    private LocalDate receivedDate;

    @NotBlank(message = "Số giấy phép không được để trống")
    @Size(max = 50, message = "Số giấy phép tối đa 50 ký tự")
    private String licenseNumber;

    @NotBlank(message = "Nước sản xuất không được để trống")
    @Size(max = 50, message = "Nước sản xuất tối đa 50 ký tự")
    private String originCountry;

    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 1, message = "Đơn giá phải lớn hơn 0")
    @Max(value = 1000000, message = "Đơn giá không được quá 1.000.000")
    private Integer price;

    @NotBlank(message = "Hàm lượng không được để trống")
    @Size(max = 20, message = "Hàm lượng tối đa 20 ký tự")
    private String dosage;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Max(value = 100000, message = "Số lượng không được quá 100000")
    private Integer quantity;

    @NotNull(message = "Hạn sử dụng không được để trống")
    private LocalDate expiryDate;

    @NotBlank(message = "Điều kiện bảo quản không được để trống")
    @Size(max = 100, message = "Điều kiện bảo quản tối đa 100 ký tự")
    private String storageConditions;

    @NotBlank(message = "Độ tuổi tiêm chủng không được để trống")
    @Size(max = 50, message = "Độ tuổi tiêm chủng tối đa 50 ký tự")
    private String vaccinationAge;
}