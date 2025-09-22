package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.BenhNhanEntity;

@Repository
public interface BenhNhanRepository extends JpaRepository<BenhNhanEntity, String> {
    Optional<BenhNhanEntity> findByHoTen(String hoTen);

    List<BenhNhanEntity> findAllByIsDeletedFalse();

    Optional<BenhNhanEntity> findById(String id);

    Optional<BenhNhanEntity> findByIdAndIsDeletedFalse(String id);


    Optional<BenhNhanEntity> findByTaiKhoan_TenDangNhapAndIsDeletedFalse(String tenDangNhap);

    Optional<BenhNhanEntity> findByTaoBoiTaiKhoan_TenDangNhapAndIsDeletedFalse(String tenDangNhap);

}
