package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.DonThuocEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng đơn thuốc.
 */
@Repository
public interface DonThuocRepository extends JpaRepository<DonThuocEntity, String> {

	/**
	 * Lấy tất cả đơn thuốc của một bệnh nhân (theo id bệnh nhân).
	 * 
	 * @param benhNhanId id của bệnh nhân
	 * @return danh sách đơn thuốc
	 */
	@Query("SELECT d FROM DonThuocEntity d WHERE d.benhNhan.id = :benhNhanId AND (d.isDeleted = false OR d.isDeleted IS NULL)")
	List<DonThuocEntity> findByBenhNhanId(@Param("benhNhanId") String benhNhanId);

	@Query("SELECT d FROM DonThuocEntity d")
    List<DonThuocEntity> findAllDonThuoc();
}