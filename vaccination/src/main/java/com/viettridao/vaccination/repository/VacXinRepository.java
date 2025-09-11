package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.VacXinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacXinRepository extends JpaRepository<VacXinEntity, String> {
    Optional<VacXinEntity> findByMaCodeAndIsDeletedFalse(String maCode);
    Optional<VacXinEntity> findByTenAndIsDeletedFalse(String ten);

}
