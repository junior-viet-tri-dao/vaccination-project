package com.viettridao.vaccination.service;

import com.viettridao.vaccination.dto.response.normalUser.VaccineScheduleResponse;

import java.util.List;

/**
 * Service cho lịch tiêm chủng cá nhân.
 */
public interface VaccineScheduleService {
    /**
     * Lấy danh sách lịch tiêm chủng cho một user cá nhân (theo id tài khoản).
     * @param userId id của tài khoản (bệnh nhân)
     * @return danh sách lịch tiêm chủng của user
     */
    List<VaccineScheduleResponse> getVaccineSchedulesForUser(String userId);
}