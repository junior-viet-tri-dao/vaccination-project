package com.viettridao.vaccination.repository;

import com.viettridao.vaccination.model.BaoCaoPhanUngEntity;
import com.viettridao.vaccination.model.BaoCaoPhanUngEntity.TrangThaiPhanHoi;
import com.viettridao.vaccination.model.KetQuaTiemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaoCaoPhanUngRepository extends JpaRepository<BaoCaoPhanUngEntity, String> {

    Optional<BaoCaoPhanUngEntity> findByKetQuaTiem_IdAndTrangThaiPhanHoiAndIsDeletedFalse(
            String ketQuaTiemId, BaoCaoPhanUngEntity.TrangThaiPhanHoi trangThaiPhanHoi
    );

    // Kiểm tra đã có phản hồi cho 1 lần tiêm chưa
    boolean existsByKetQuaTiem_IdAndIsDeletedFalse(String ketQuaTiemId);

    // Lọc theo trạng thái phản hồi và chưa xóa
    List<BaoCaoPhanUngEntity> findByTrangThaiPhanHoiAndIsDeletedFalse(TrangThaiPhanHoi trangThaiPhanHoi);

    // Lấy báo cáo phản ứng theo id và chưa xóa
    Optional<BaoCaoPhanUngEntity> findByIdAndIsDeletedFalse(String id);

    // Lấy tất cả báo cáo của 1 bệnh nhân (nếu cần)
    List<BaoCaoPhanUngEntity> findByBenhNhan_IdAndIsDeletedFalse(String benhNhanId);

    // Lấy danh sách phản hồi theo mã bệnh nhân
    List<BaoCaoPhanUngEntity> findByBenhNhanId(String benhNhanId);

    // Lấy phản hồi theo kết quả tiêm
    List<BaoCaoPhanUngEntity> findByKetQuaTiemId(String ketQuaTiemId);


    // Tìm báo cáo theo bệnh nhân, tình trạng kết quả tiêm và trạng thái phản hồi
    List<BaoCaoPhanUngEntity> findByKetQuaTiem_BenhNhan_IdAndKetQuaTiem_TinhTrangAndTrangThaiPhanHoi(
            String benhNhanId,
            KetQuaTiemEntity.TinhTrangTinhTrang tinhTrang,
            BaoCaoPhanUngEntity.TrangThaiPhanHoi trangThaiPhanHoi
    );

    boolean existsByKetQuaTiem_Id(String ketQuaTiemId);

    boolean existsByKetQuaTiem(KetQuaTiemEntity ketQuaTiem);

    List<BaoCaoPhanUngEntity> findAllByBenhNhan_Id(String benhNhanId);

    // Lấy báo cáo của bệnh nhân KHÔNG có trạng thái DA_PHAN_HOI
    List<BaoCaoPhanUngEntity> findAllByBenhNhan_IdAndTrangThaiPhanHoiNot(
            String benhNhanId,
            BaoCaoPhanUngEntity.TrangThaiPhanHoi trangThaiPhanHoi
            
    );
    
 // Kiểm tra tồn tại báo cáo theo kết quả tiêm + trạng thái phản hồi
    boolean existsByKetQuaTiemIdAndTrangThaiPhanHoi(
            String ketQuaTiemId,
            BaoCaoPhanUngEntity.TrangThaiPhanHoi trangThaiPhanHoi
    );

}