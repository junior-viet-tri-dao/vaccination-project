package com.viettridao.vaccination.repository;

import java.util.List;
import java.util.Optional;

import com.viettridao.vaccination.model.BenhNhanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.vaccination.model.KetQuaTiemEntity;

@Repository
public interface KetQuaTiemRepository extends JpaRepository<KetQuaTiemEntity, String> {

    // Lấy danh sách kết quả tiêm của một bệnh nhân, lọc chưa xóa, sắp xếp theo ngày
    // tiêm tăng dần
    List<KetQuaTiemEntity> findByBenhNhan_IdAndIsDeletedFalseOrderByNgayTiemAsc(String benhNhanId);

    List<KetQuaTiemEntity> findByBenhNhanId(String maBenhNhan);

    List<KetQuaTiemEntity> findByLichTiemId(String maLich);

    List<KetQuaTiemEntity> findByNguoiThucHienId(String maNguoiThucHien);

    List<KetQuaTiemEntity> findByIsDeletedFalse();

    Optional<KetQuaTiemEntity> findTopByBenhNhan_HoTenOrderByNgayTiemDesc(String hoTenBenhNhan);

    // Lấy tất cả kết quả tiêm theo bệnh nhân (lọc chưa xóa)
    List<KetQuaTiemEntity> findAllByBenhNhan_IdAndIsDeletedFalse(String maBenhNhan);

    // Lấy tất cả kết quả tiêm chưa xóa
    List<KetQuaTiemEntity> findAllByIsDeletedFalse();

    // Lấy kết quả tiêm theo mã bệnh nhân (field trực tiếp)
    List<KetQuaTiemEntity> findAllByBenhNhanIdAndIsDeletedFalse(String maBenhNhan);

    // Alias khác cho việc tìm theo mã bệnh nhân
    List<KetQuaTiemEntity> findByBenhNhanIdAndIsDeletedFalse(String benhNhanId);

    List<KetQuaTiemEntity> findByBenhNhanIdAndTinhTrangAndIsDeletedFalse(
            String benhNhanId,
            KetQuaTiemEntity.TinhTrangTinhTrang tinhTrang
    );

    List<KetQuaTiemEntity> findAllByBenhNhanAndTinhTrang(
            BenhNhanEntity benhNhan, KetQuaTiemEntity.TinhTrangTinhTrang tinhTrang
    );

    List<KetQuaTiemEntity> findByBenhNhan_IdAndTinhTrang(String benhNhanId, KetQuaTiemEntity.TinhTrangTinhTrang tinhTrang);

    List<KetQuaTiemEntity> findByBenhNhanIdAndTinhTrang(String benhNhanId, KetQuaTiemEntity.TinhTrangTinhTrang tinhTrang);
}