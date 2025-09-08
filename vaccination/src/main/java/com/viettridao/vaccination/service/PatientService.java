package com.viettridao.vaccination.service;

import java.util.List;

public interface PatientService {
    List<String> getAllPatientCodes();   // trả về danh sách mã bệnh nhân
    String getPatientNameByCode(String patientCode); // trả về tên theo mã
}
