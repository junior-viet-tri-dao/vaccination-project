package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.EpidemicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * EpidemicRepository
 * Repository interface cho entity EpidemicEntity.
 * Hỗ trợ CRUD + phân trang + sorting nhờ JpaRepository.
 */
@Repository
public interface EpidemicRepository extends JpaRepository<EpidemicEntity, String> {

    /**
     * Tìm tất cả Epidemic có phân trang.
     *
     * @param pageable đối tượng Pageable (page, size, sort)
     * @return Page<EpidemicEntity>
     */
    @Override
    Page<EpidemicEntity> findAll(Pageable pageable);

    /**
     * Ví dụ filter: tìm theo tên dịch bệnh có chứa keyword (phân trang).
     *
     * @param epidemicName keyword để tìm kiếm theo tên dịch bệnh
     * @param pageable     đối tượng Pageable (page, size, sort)
     * @return Page<EpidemicEntity>
     */
    Page<EpidemicEntity> findByEpidemicNameContainingIgnoreCase(String epidemicName, Pageable pageable);

    /**
     * Ví dụ filter: tìm theo địa chỉ có chứa keyword (phân trang).
     *
     * @param address  keyword địa chỉ
     * @param pageable đối tượng Pageable (page, size, sort)
     * @return Page<EpidemicEntity>
     */
    Page<EpidemicEntity> findByAddressContainingIgnoreCase(String address, Pageable pageable);
}