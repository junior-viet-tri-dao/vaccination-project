package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.LoVacXinEntity;
import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface LoVacXinRepository extends JpaRepository<LoVacXinEntity, String> {
    Optional<LoVacXinEntity> findByMaLoCodeIgnoreCaseAndIsDeletedFalse(String maLoCode);

    Optional<LoVacXinEntity> findByVacXinMaCode(String maCode);

    List<LoVacXinEntity> findAllByIsDeletedFalse();

    Optional<LoVacXinEntity> findFirstByVacXinAndSoLuongGreaterThan(VacXinEntity vacXin, Integer soLuong);
    
    List<LoVacXinEntity> findByVacXinAndSoLuongGreaterThanOrderByHanSuDungAsc(VacXinEntity vacXin, int soLuong);

}
