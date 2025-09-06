package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.VaccineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<VaccineEntity, String> {

}
