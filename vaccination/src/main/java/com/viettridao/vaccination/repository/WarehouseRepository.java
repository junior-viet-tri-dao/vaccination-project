package com.viettridao.vaccination.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.VaccineBatchEntity;
import com.viettridao.vaccination.model.VaccineEntity;

@Repository
public interface WarehouseRepository extends JpaRepository<VaccineBatchEntity, String> {

	/**
	 * Lấy tất cả (có phân trang)
	 *
	 * @param pageable thông tin phân trang
	 * @return danh sách phân trang VaccineBatchEntity
	 */
	@Override
	Page<VaccineBatchEntity> findAll(Pageable pageable);

	@Query("SELECT v FROM VaccineEntity v WHERE LOWER(v.vaccineName) = LOWER(:name) AND LOWER(v.vaccineType.vaccineTypeName) = LOWER(:type)")
	Optional<VaccineEntity> findVaccineByNameAndType(@Param("name") String name, @Param("type") String type);

	/**
	 * Tìm kiếm theo tên vắc xin
	 */
	Page<VaccineBatchEntity> findByVaccine_VaccineNameContainingIgnoreCase(String name, Pageable pageable);

	/**
	 * Tìm kiếm theo loại vắc xin
	 */
	Page<VaccineBatchEntity> findByVaccine_VaccineType_VaccineTypeNameContainingIgnoreCase(String typeName,
			Pageable pageable);

	/**
	 * Tìm kiếm theo nơi sản xuất (countryOfOrigin)
	 */
	Page<VaccineBatchEntity> findByCountryOfOriginContainingIgnoreCase(String origin, Pageable pageable);

	/**
	 * Tìm kiếm theo độ tuổi
	 */
	Page<VaccineBatchEntity> findByVaccine_AgeGroupContainingIgnoreCase(String ageGroup, Pageable pageable

	);
    
    Optional<VaccineBatchEntity> findByBatchCodeIgnoreCase(String batchCode);
}