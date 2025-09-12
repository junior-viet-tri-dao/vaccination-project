package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.BienDongKhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienDongKhoRepository extends JpaRepository<BienDongKhoEntity, String> {

}