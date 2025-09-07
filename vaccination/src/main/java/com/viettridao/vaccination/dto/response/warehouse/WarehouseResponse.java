package com.viettridao.vaccination.dto.response.warehouse;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO Response cho màn hình hiển thị danh sách lô vắc xin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {
    private String batchCode;           // Mã lô
    private String vaccineName;       // Tên vắc xin
    private String vaccineTypeName;   // Loại vắc xin
    private LocalDate receivedDate;   // Ngày nhận
    private String licenseNumber;     // Số giấy phép
    private String countryOfOrigin;   // Nước sản xuất
    private String dosage;            // Hàm lượng
    private Integer quantity;         // Số lượng
    private LocalDate expirationDate; // Hạn sử dụng
    private String storageCondition;  // Điều kiện bảo quản
    private String ageGroup;          // Độ tuổi
    private String status;            // Tình trạng
}

