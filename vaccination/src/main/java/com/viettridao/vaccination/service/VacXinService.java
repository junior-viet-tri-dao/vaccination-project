package com.viettridao.vaccination.service;

import java.util.List;

import com.viettridao.vaccination.model.VacXinEntity;

public interface VacXinService {
    List<VacXinEntity> findAll(); // tất cả
    List<VacXinEntity> getAllActiveVaccines(); // chưa xóa
    List<VacXinEntity> getAllVaccines(); // thêm mới
    List<VacXinEntity> getAllVacXin();

}

