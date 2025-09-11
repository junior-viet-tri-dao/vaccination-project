package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.VacXinEntity;

@Repository
public interface VacXinRepository extends JpaRepository<VacXinEntity, String> {

	Optional<VacXinEntity> findByMaCode(String maCode);

	// Nếu bạn có xóa mềm:
	Optional<VacXinEntity> findByMaCodeAndIsDeletedFalse(String maCode);
	
    List<VacXinEntity> findAllByIsDeletedFalse();
   
    

}
