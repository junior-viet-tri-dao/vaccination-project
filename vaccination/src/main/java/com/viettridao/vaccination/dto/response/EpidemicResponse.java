package com.viettridao.vaccination.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * EpidemicResponse
 * DTO dùng để trả dữ liệu dịch bệnh ra màn hình Thymeleaf.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpidemicResponse {

    /**
     * Số thứ tự hiển thị trong bảng
     */
    private int index;

    /**
     * Thời điểm khảo sát dịch bệnh
     */
    private LocalDate surveyTime;

    /**
     * Địa chỉ nơi khảo sát
     */
    private String address;

    /**
     * Tên loại bệnh dịch
     */
    private String epidemicName;

    /**
     * Số người bị nhiễm bệnh
     */
    private Integer infectedCount;

    /**
     * Đường lây nhiễm (ví dụ: Hô hấp, Tiêu hóa, Máu,...)
     */
    private String transmissionMode;

    /**
     * Tác hại sức khỏe gây ra bởi bệnh dịch
     */
    private String healthImpact;

    /**
     * Loại vắc xin phòng bệnh (nếu có).
     * Thuộc tính này hiện Entity chưa có,
     * nhưng bạn có thể thêm field vào DB hoặc ánh xạ thủ công khi hiển thị.
     */
    private String vaccineType;

    /**
     * Ghi chú thêm (nếu có).
     */
    private String note;
}
