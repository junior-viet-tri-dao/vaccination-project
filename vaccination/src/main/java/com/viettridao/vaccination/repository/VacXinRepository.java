package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface VacXinRepository extends JpaRepository<VacXinEntity, String> {
	Optional<VacXinEntity> findByMaCodeAndIsDeletedFalse(String maCode);

	Optional<VacXinEntity> findByTenAndIsDeletedFalse(String ten);

	Page<VacXinEntity> findByMaCodeContainingIgnoreCaseAndIsDeletedFalse(String maCode, Pageable pageable);

	Page<VacXinEntity> findByTenContainingIgnoreCaseAndIsDeletedFalse(String ten, Pageable pageable);

	Page<VacXinEntity> findByMoTaContainingIgnoreCaseAndIsDeletedFalse(String moTa, Pageable pageable);

	Optional<VacXinEntity> findByMaCode(String maCode);

	List<VacXinEntity> findAllByIsDeletedFalse();

    List<VacXinEntity> findByIsDeletedFalse();
    
    

}
