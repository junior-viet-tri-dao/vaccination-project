package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.VaiTroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTroEntity, String> {
    @Query("select v from VaiTroEntity v join fetch v.quyenHans where v.ten = :ten")
    Optional<VaiTroEntity> findByTenWithPermissions(String ten);

    Optional<VaiTroEntity> findByTen(String ten);

    List<VaiTroEntity> findAllByIdInAndIsDeletedFalse(List<String> ids);

    List<VaiTroEntity> findAllByIsDeletedFalse();
}
