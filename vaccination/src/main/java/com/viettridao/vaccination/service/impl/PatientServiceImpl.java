package com.viettridao.vaccination.service.impl;

import com.viettridao.vaccination.model.PatientEntity;
import com.viettridao.vaccination.repository.PatientRepository;
import com.viettridao.vaccination.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<String> getAllPatientCodes() {
        return patientRepository.findByIsDeletedFalse()
                .stream()
                .map(PatientEntity::getPatientCode)
                .toList();
    }

    @Override
    public String getPatientNameByCode(String patientCode) {
        return patientRepository.findByPatientCode(patientCode)
                .map(PatientEntity::getPatientName)
                .orElse(null);
    }
}
