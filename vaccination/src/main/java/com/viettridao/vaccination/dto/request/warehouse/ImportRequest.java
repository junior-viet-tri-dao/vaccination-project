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
    @Size(min = 2, max = 20, message = "Tên vắc-xin từ 2 đến 20 ký tự")
    private String vaccineName;

    @NotBlank(message = "Loại vắc-xin không được để trống")
    @Size(max = 20, message = "Loại vắc-xin tối đa 20 ký tự")
    private String vaccineType;

    @NotNull(message = "Ngày nhập không được để trống")
    private LocalDate receivedDate;

    @NotBlank(message = "Số giấy phép không được để trống")
    @Size(max = 20, message = "Số giấy phép tối đa 20 ký tự")
    private String licenseNumber;

    @NotBlank(message = "Nước sản xuất không được để trống")
    @Size(max = 10, message = "Nước sản xuất tối đa 10 ký tự")
    private String originCountry;

    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 1, message = "Đơn giá phải lớn hơn 0")
    @Max(value = 1000000, message = "Đơn giá không được quá 1.000.000")
    private Integer price;

    @NotBlank(message = "Hàm lượng không được để trống")
    @Size(max = 10, message = "Hàm lượng tối đa 10 ký tự")
    private String dosage;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Max(value = 100000, message = "Số lượng không được quá 100000")
    private Integer quantity;

    @NotNull(message = "Hạn sử dụng không được để trống")
    private LocalDate expiryDate;

    @NotBlank(message = "Điều kiện bảo quản không được để trống")
    @Size(max = 30, message = "Điều kiện bảo quản tối đa 30 ký tự")
    private String storageConditions;

    @NotBlank(message = "Độ tuổi tiêm chủng không được để trống")
    @Size(max = 10, message = "Độ tuổi tiêm chủng tối đa 10 ký tự")
    private String vaccinationAge;
}