package com.viettridao.vaccination.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.TaiKhoanEntity;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoanEntity, String> {
    /**
     * Tìm tài khoản theo tên đăng nhập.
     *
     * @param username tên đăng nhập
     * @return Optional<AccountEntity>
     */
    @Query("SELECT a FROM TaiKhoanEntity a WHERE a.tenDangNhap = :tenDangNhap")
    Optional<TaiKhoanEntity> findByUsername(String tenDangNhap);
}
