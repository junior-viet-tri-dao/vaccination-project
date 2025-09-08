package com.viettridao.vaccination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viettridao.vaccination.model.VaccineEntity;

public interface VaccineRepository extends JpaRepository<VaccineEntity, String> {

}
