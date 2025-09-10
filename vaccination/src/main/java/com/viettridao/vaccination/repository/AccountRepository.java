package com.viettridao.vaccination.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.TaiKhoanEntity;

@Repository
public interface AccountRepository extends JpaRepository<TaiKhoanEntity, UUID> {
    /**
     * Tìm tài khoản theo tên đăng nhập.
     *
     * @param username tên đăng nhập
     * @return Optional<AccountEntity>
     */
    @Query("SELECT a FROM TaiKhoanEntity a WHERE a.username = :username")
    Optional<TaiKhoanEntity> findByUsername(String username);
}
